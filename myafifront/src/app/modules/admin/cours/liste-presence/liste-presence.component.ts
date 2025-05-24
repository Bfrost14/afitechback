import { ViewChild, AfterViewInit, Component, OnInit, Input } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortable } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { FormArray, UntypedFormBuilder, UntypedFormGroup, FormControl } from '@angular/forms';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
import { AbsenceService } from '../service/absence.service';
import { NewCalendrierCours } from '../../calendrier-cours/calendrier-cours.model';
import dayjs from 'dayjs/esm';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar } from '@angular/material/snack-bar';

interface SearchFild {
  key: string;
  value: any;
}

interface PresenceForm {
  id: number | null;
  presence: boolean;
  justifie: boolean | null;
  user: NewUtilisateur;
}

@Component({
  selector: 'app-liste-presence',
  styleUrls: ['./liste-presence.component.scss'],
  templateUrl: './liste-presence.component.html',
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class ListePresenceComponent implements OnInit, AfterViewInit {
  @Input() cours: any;
  @Input() etudiant: any;

  // Configuration du tableau
  columnsToDisplay = ['select', 'matricule', 'nom', 'presence'];
  displayedColumns: string[] = [...this.columnsToDisplay];
  dataSource = new MatTableDataSource<NewUtilisateur>();
  selection = new SelectionModel<NewUtilisateur>(true, []);

  // États du composant
  hasPresences: boolean = false;
  showSaveButton: boolean = true;
  canEditPresence: boolean = false;
  editWindowEnd: dayjs.Dayjs;

  // Formulaires
  presenceForm: UntypedFormGroup;
  presences: FormArray;

  // Pagination et tri
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  pageSize = 25;
  pageSizeOptions: number[] = [25, 50, 100];

  constructor(
    private _etudiantService: AdminService,
    private _presenceService: AbsenceService,
    private _formBuilder: UntypedFormBuilder,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.presenceForm = this._formBuilder.group({
      presences: this._formBuilder.array([])
    });
    this.presences = this.presenceForm.get('presences') as FormArray;

    if (this.etudiant != undefined) {
      this.displayedColumns.pop()
      this.displayedColumns.pop()
      this.displayedColumns.pop()
      this.displayedColumns.pop()
      this.displayedColumns.push("matiere")
      this.displayedColumns.push("dateDebut")
      this.displayedColumns.push("dateFin")
      this.displayedColumns.push("presence")
    } else {
      // Calcul de la fenêtre d'édition (date de fin + 15 minutes)
      this.editWindowEnd = dayjs(this.cours.dateFin).add(15, 'minute');
      this.checkEditPermission();
    }

  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.sort.sort({ id: 'id', start: 'desc' } as MatSortable);
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    if (this.etudiant != undefined) {
      this.loadPresencesEtudiant();
    } else {
      this.loadPresences();
    }

  }

  checkEditPermission() {
    const now = dayjs();
    this.canEditPresence = now.isBefore(this.editWindowEnd) && now.isAfter(dayjs(this.cours.dateDebut));

    if (!this.canEditPresence) {
      const message = now.isBefore(dayjs(this.cours.dateDebut))
        ? `La saisie des présences sera disponible à partir du ${dayjs(this.cours.dateDebut).format('DD/MM/YYYY HH:mm')}`
        : `La période de saisie des présences est terminée (elle se terminait le ${this.editWindowEnd.format('DD/MM/YYYY HH:mm')})`;

      this._snackBar.open(message, 'OK', { duration: 5000 });
    }
  }

  loadPresences() {
    this.isLoadingResults = true;
    this._presenceService.query({
      page: this.paginator?.pageIndex || 0,
      size: 100,
      sort: this.sort?.active + "," + this.sort?.direction,
      idCalendrierCours: this.cours.id
    }).subscribe(response => {
      if (response.body.data.length > 0) {
        this.hasPresences = true;
        this.showSaveButton = this.canEditPresence;
        this.dataSource.data = response.body.data.map(item => item.user);
        this.createPresenceForms(response.body.data);
      } else {
        this.hasPresences = false;
        this.showSaveButton = this.canEditPresence;
        this.loadEtudiants();
      }
      this.resultsLength = response.body.pagination.length;
      this.isLoadingResults = false;
    });
  }

  loadPresencesEtudiant() {
    this.isLoadingResults = true;
    this._presenceService.query({
      page: this.paginator?.pageIndex || 0,
      size: 100,
      sort: this.sort?.active + "," + this.sort?.direction,
      etudiant: this.etudiant.email
    }).subscribe(response => {

      this.hasPresences = true;
      this.showSaveButton = false;
      this.dataSource.data = response.body.data;
      this.createPresenceForms(response.body.data);

      this.resultsLength = response.body.pagination.length;
      this.isLoadingResults = false;
    });
  }

  loadEtudiants() {
    this._etudiantService.query({
      page: this.paginator?.pageIndex || 0,
      size: 100,
      sort: this.sort?.active + "," + this.sort?.direction,
      profil: "ETUDIANT",
      filiere: this.cours.matiereUser.filiere.nom
    }).subscribe(response => {
      this.dataSource.data = response.body.data;
      this.createEmptyPresenceForms(response.body.data);
      this.resultsLength = response.body.pagination.length;
      this.isLoadingResults = false;
    });
  }

  createPresenceForms(absences: any[]) {
    this.presences.clear();
    absences.forEach(absence => {
      this.presences.push(this._formBuilder.group({
        id: [absence.id],
        presence: [{ value: absence.presence, disabled: true }], // ⛔ présence non modifiable
        justifie: [{ value: absence.justifie, disabled: absence.presence }], // ✅ justification modifiable si absent
        user: [absence.user]
      }));
    });
  }


  createEmptyPresenceForms(etudiants: NewUtilisateur[]) {
    this.presences.clear();
    etudiants.forEach(etudiant => {
      this.presences.push(this._formBuilder.group({
        id: [null],
        presence: [false],
        justifie: [null],
        user: [etudiant]
      }));
    });
  }

  savePresences() {
    if (!this.canEditPresence) {
      this._snackBar.open('La période de modification des présences est terminée', 'OK', { duration: 3000 });
      return;
    }

    const presencesToSave = this.presenceForm.value.presences
      .filter((_, index) => this.selection.isSelected(this.dataSource.data[index]))
      .map(presence => ({
        id: presence.id,
        presence: presence.presence,
        justifie: presence.presence ? null : presence.justifie,
        calendierCours: this.cours,
        user: presence.user
      }));

    const operation = this.hasPresences
      ? this._presenceService.update(presencesToSave)
      : this._presenceService.create(presencesToSave);

    operation.subscribe({
      next: () => {
        this._snackBar.open('Présences enregistrées avec succès', 'OK', { duration: 3000 });
        this.loadPresences();
        this.selection.clear();
      },
      error: (err) => {
        console.error('Erreur lors de la sauvegarde', err);
        this._snackBar.open('Erreur lors de l\'enregistrement des présences', 'OK', { duration: 3000 });
      }
    });
  }

  toggleJustification(index: number) {
    const presenceControl = this.presences.at(index).get('presence');
    const justificationControl = this.presences.at(index).get('justifie');

    if (presenceControl.value) {
      justificationControl.setValue(null);
      justificationControl.disable();
    } else {
      justificationControl.enable();
    }
  }


  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }
}