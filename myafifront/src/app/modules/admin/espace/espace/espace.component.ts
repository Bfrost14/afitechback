import { Component, OnInit } from '@angular/core';
import { AuthService } from 'app/core/auth/auth.service';
import { AdminService } from '../../user/service/admin.service';
import { finalize } from 'rxjs/operators';
import { NewUtilisateur } from '../../ue/ue.model';
import { FiliereService } from '../../filiere/service/filiere.service';
import { CampusService } from '../../campus/service/campus.service';
import { NewCampus } from '../../campus/campus.model';
import { NewFiliere } from '../../filiere/filiere.model';

@Component({
  selector: 'app-espace',
  templateUrl: './espace.component.html',
  styleUrls: ['./espace.component.css']
})
export class EspaceComponent implements OnInit {
  etudiant: any | null = null;
  loading = true;
  error: string | null = null;
  activeTab: 'profile' | 'grades' | 'attendance' | 'courses' = 'profile';
  tabs = [
    { id: 'profile', label: 'Profil', icon: 'person_outline' },
    { id: 'grades', label: 'Notes', icon: 'school' },
    { id: 'attendance', label: 'Présence', icon: 'calendar_today' },
    { id: 'courses', label: 'Cours', icon: 'menu_book' }
  ];

  statistiquesParSemestreList: { semestre: string, data: any }[] = [];
  filieres: any[] = []
  campuses: any[] = []
  data: any[] = []
  nom: string = "";
  prenom: string = "";
  matricule: string = "";
  filiere: string = "";
  campus: any;
  tag: number = 0;

  constructor(
    private _authService: AuthService,
    private _etudiantService: AdminService,
    private _filiereService: FiliereService,
    private _campusService: CampusService
  ) { }

  ngOnInit(): void {
    this.getEtudiant();
  }

  getEtudiant(): void {
    const userId = this._authService.getUtilisateur()?.email;
    if (!userId) {
      this.error = 'Utilisateur non connecté';
      this.loading = false;
      return;
    }

    this.loading = true;
    this.error = null;

    this._etudiantService.get(userId)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (data: any) => {


          if (data?.filire == null) {
            this.tag = 1
            this.getAllCampus()
          } else {
            this.etudiant = data;
            this.statistiquesParSemestreList = this.etudiant.statsParSemestre
          }
          console.log('Données étudiant:', data);
        },
        error: (err) => {
          console.error('Erreur lors du chargement:', err);
          this.error = 'Échec du chargement des données. Veuillez réessayer.';
        }
      });


  }

  changeTab(tabId: 'profile' | 'grades' | 'attendance' | 'courses'): void {
    this.activeTab = tabId;
  }

  refreshData(): void {
    this.getEtudiant();
  }


  getAllFiliere(campus: NewCampus, nom: any = "") {
    this.campus = campus.nom
    this._filiereService.query({ page: 0, size: 10, nom: nom, campus: this.campus }).subscribe(
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

  getAllEtudiant(filiere: NewFiliere, matricule: string, prenom: string, nom: string) {
    this.filiere = filiere.nom
    this.matricule = matricule;
    this.prenom = prenom;
    this.nom = nom;

    return this._etudiantService
      .query(
        {
          page: 0,
          size: 10,
          sort: "lastName,asc",
          prenom: this.prenom, nom: this.nom, matricule: this.matricule, profil: "ETUDIANT", filiere: this.filiere
        }
      )
      .subscribe((data) => {
        console.log(
          '@@@@@@@@@@@@@@@@@@@@@@@@ etudiant data @@@@@@@@@@@@',
          data
        );
        this.data = data.body.data;
      });
  }

  getEtudiantBySearch(email: string): void {


    this._etudiantService.get(email)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (data: any) => {
          this.tag = 2
          this.etudiant = data;
          this.statistiquesParSemestreList = this.etudiant.statsParSemestre

          console.log('Données étudiant:', data);
        },
        error: (err) => {
          console.error('Erreur lors du chargement:', err);
          this.error = 'Échec du chargement des données. Veuillez réessayer.';
        }
      });


  }

  toggleSearch(): void {
    if (this.tag === 1) {
      this.tag = 3; // Mode recherche ouvert
    } else if (this.tag === 3) {
      this.tag = 1; // Mode recherche fermé
    }
  }
}