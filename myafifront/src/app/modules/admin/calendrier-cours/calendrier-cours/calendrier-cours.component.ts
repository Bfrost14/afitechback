// calendrier-cours.component.ts
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import dayjs from 'dayjs/esm';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CalendrierCoursService } from '../service/calendrier-cours.service';
import { CalendrierCoursMultiDialogComponent } from '../calendrier-cours-multi-dialog/calendrier-cours-multi-dialog.component';
import { CalendrierCoursModalComponent } from '../calendrier-cours-modal/calendrier-cours-modal.component';
import { FiliereService } from '../../filiere/service/filiere.service';
import { SalleService } from '../../salle/service/salle.service';
import { MatiereService } from '../../matiere/service/matiere.service';
import { CampusService } from '../../campus/service/campus.service';
import { addDays, addMonths, addWeeks, endOfDay, endOfMonth, endOfWeek, startOfDay, startOfMonth, startOfWeek, subDays, subMonths, subWeeks } from 'date-fns';
import { AuthService } from 'app/core/auth/auth.service';
import { AdminService } from '../../user/service/admin.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-calendrier-cours',
  templateUrl: './calendrier-cours.component.html',
  styleUrls: ['./calendrier-cours.component.scss']
})
export class CalendrierCoursComponent implements OnInit {
  view: CalendarView = CalendarView.Week;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  filterForm: FormGroup;

  filieres: any[] = []
  matieres: any[] = []
  salles: any[] = []
  campuses: any[] = []
  selectedFiliere: string = ""
  selectedCampus: string = ""
  showFilters = false;
  etudiant: any = {filiere: null};


  constructor(
    private dialog: MatDialog,
    private calendrierCoursService: CalendrierCoursService,
    private fb: FormBuilder,
    private _filiereService: FiliereService,
    private _matiereService: MatiereService,
    private _salleService: SalleService,
    private _campusService: CampusService,
    private _authService: AuthService,
    private etudiantService: AdminService
  ) {
    this.filterForm = this.fb.group({
      page: 0,
      size: 100,
      sort: 'id,desc',
      dateDebut: [null],
      dateFin: [null],
      matiereUser: [null],
      salle: [null],
      filiere: [null],
      matiere: [null],
      campus: [null],
    });
  }

  ngOnInit(): void {
    this.getEtudiantBySearch(this._authService.getUtilisateur().email)
    
  }

  loadCours(): void {
    if (this.filterForm.value.dateDebut != null) {
      this.filterForm.value.dateDebut = new Date(this.filterForm.value.dateDebut).toISOString();
    }
    if (this.filterForm.value.dateFin != null) {
      this.filterForm.value.dateFin = new Date(this.filterForm.value.dateFin).toISOString();
    }


    const filters = this.filterForm.value;

    this.calendrierCoursService.query(filters).subscribe(response => {
      const coursList = response.body?.data || [];

      this.events = coursList.map(cours => this.mapCoursToEvent(cours));
    });
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(CalendrierCoursModalComponent, {
      width: '600px',
      data: { mode: 'add' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'saved') {
        this.loadCours();
      }
    });
  }

  openAddMultipleDialog(): void {
    const dialogRef = this.dialog.open(CalendrierCoursMultiDialogComponent, {
      width: '800px',
      data: { mode: 'add' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'saved') {
        this.loadCours();
      }
    });
  }

  openEditDialog(event: CalendarEvent): void {
    const dialogRef = this.dialog.open(CalendrierCoursModalComponent, {
      width: '600px',
      data: { mode: 'edit', cours: event.meta }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'saved') {
        this.loadCours();
      }
    });
  }

  setView(view: CalendarView): void {
    this.view = view;
  }

  applyFilters(): void {
    this.loadCours();
  }

  resetFilters(): void {
    this.filterForm.reset();
    this.loadCours();
  }


  getAllSalles(nom: any = "") {
    this._salleService.query({ page: 0, size: 10, numero: nom, campus: this.selectedCampus }).subscribe(
      response => {
        this.salles = response.body["data"];
      }
    )
  }


  getAllMatiere(nom: any = "") {
    this._matiereService.query({ page: 0, size: 10, matiere: nom, filiere: this.selectedFiliere }).subscribe(
      response => {
        this.matieres = response.body["data"];
      }
    )
  }

  getAllFiliere(nom: any = "") {
    this._filiereService.query({ page: 0, size: 10, nom: nom }).subscribe(
      response => {
        this.filieres = response.body["data"];
      }
    )
  }

  getAllCampus(nom: any = "") {
    this._campusService.query({ page: 0, size: 10, nom: nom }).subscribe(
      response => {
        this.campuses = response.body["data"];
      }
    )
  }

  displayFn(item: any): string {
    return item && item.nom ? item.nom : '';
  }

  displayFn2(item: any): string {
    return item && item.matiere ? item.matiere.nom : '';
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  displayFn3(item: any) {
    return item?.numero || '';
  }


  // Navigation
  previous(): void {
    switch (this.view) {
      case CalendarView.Day:
        this.viewDate = subDays(this.viewDate, 1);
        break;
      case CalendarView.Week:
        this.viewDate = subWeeks(this.viewDate, 1);
        break;
      case CalendarView.Month:
        this.viewDate = subMonths(this.viewDate, 1);
        break;
    }
    this.loadEventsForCurrentView();
  }

  next(): void {
    switch (this.view) {
      case CalendarView.Day:
        this.viewDate = addDays(this.viewDate, 1);
        break;
      case CalendarView.Week:
        this.viewDate = addWeeks(this.viewDate, 1);
        break;
      case CalendarView.Month:
        this.viewDate = addMonths(this.viewDate, 1);
        break;
    }
    this.loadEventsForCurrentView();
  }

  today(): void {
    this.viewDate = new Date();
    this.loadEventsForCurrentView();
  }
  loadEventsForCurrentView(): void {
    let start: Date;
    let end: Date;

    switch (this.view) {
      case CalendarView.Day:
        start = startOfDay(this.viewDate);
        end = endOfDay(this.viewDate);
        break;
      case CalendarView.Week:
        start = startOfWeek(this.viewDate, { weekStartsOn: 1 }); // lundi
        end = endOfWeek(this.viewDate, { weekStartsOn: 1 });
        break;
      case CalendarView.Month:
        start = startOfMonth(this.viewDate);
        end = endOfMonth(this.viewDate);
        break;
    }

  
    this.filterForm.value.dateDebut = start.toISOString()
     this.filterForm.value.dateFin = end.toISOString()

    this.calendrierCoursService.query(this.filterForm.value).subscribe(response => {
      const coursList = response.body?.data || [];
      this.events = coursList.map(cours => this.mapCoursToEvent(cours));
    });
  }


  private mapCoursToEvent(cours: any): CalendarEvent {
    const matiere = cours?.matiereUser?.matiere?.nom ?? 'Matière';
    const campus = cours?.salle?.campus?.nom ?? 'Campus';
    const salle = cours?.salle?.numero ?? 'Salle';
    const filiere = cours?.matiereUser?.filiere?.nom ?? 'Filière';
    const professeurPrenom = cours?.matiereUser?.user?.firstName ?? '';
    const professeurNom = cours?.matiereUser?.user?.lastName ?? '';

    return {
      start: cours?.dateDebut ? new Date(cours.dateDebut) : new Date(),
      end: cours?.dateFin ? new Date(cours.dateFin) : new Date(),
      title: `Filière: ${filiere} - ${matiere} - Campus: ${campus} \n - Salle: ${salle} - Professeur: ${professeurPrenom} ${professeurNom}`,
      meta: cours,
      allDay: false, // facultatif : true si cours sur toute la journée
      color: {
        primary: '#1e90ff',
        secondary: '#d1e8ff'
      },
      draggable: false,
      resizable: {
        beforeStart: false,
        afterEnd: false
      }
    };
  }

  getEtudiantBySearch(email: string): void {
  
  
      this.etudiantService.get(email)
        .subscribe({
          next: (data: any) => {
            
            this.etudiant = data;
            if(this.etudiant.filiere != null){
              this.filterForm.get("filiere").setValue(this.etudiant.filiere.nom)
              this.filterForm.get("campus").setValue(this.etudiant.campus.nom)
            }else{
              this.getAllFiliere();
              this.getAllCampus();
            }
            this.loadCours();
          },
          error: (err) => {
            console.error('Erreur lors du chargement:', err);
          }
        });
  
  
    }
  
}