// calendrier-cours-dialog.component.ts
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import dayjs from 'dayjs/esm';
import { CalendrierCoursService } from '../service/calendrier-cours.service';
import { FiliereService } from '../../filiere/service/filiere.service';
import { SalleService } from '../../salle/service/salle.service';
import { MatiereUtilisateurService } from '../../matiere-utilisateur/service/matiere-utilisateur.service';
import { CampusService } from '../../campus/service/campus.service';

@Component({
  selector: 'app-calendrier-cours-modal',
  templateUrl: './calendrier-cours-modal.component.html',
  styleUrls: ['./calendrier-cours-modal.component.css']
})
export class CalendrierCoursModalComponent implements OnInit {
  form: FormGroup;
  isSaving = false;
  filieres: any[] = [];
  matieres: any[] = [];
  salles: any[] = [];
  campuses: any[] = [];
  selectedFiliere: any;
  selectedCampus: string = ""

  constructor(
    public dialogRef: MatDialogRef<CalendrierCoursModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private calendrierCoursService: CalendrierCoursService,
    private filiereService: FiliereService,
    private matiereService: MatiereUtilisateurService,
    private salleService: SalleService,
    private _campusService: CampusService
  ) {
    this.form = this.fb.group({
      id: [null],
      dateDebut: [null, Validators.required],
      dateFin: [null, Validators.required],
      matiereUser: [null, Validators.required],
      salle: [null, Validators.required],
      filiere: [null, Validators.required],
      campus: [null, Validators.required],
      lien: [null],
    });

    if (this.data.mode === 'edit' && this.data.cours) {
      this.form.patchValue({
        id: this.data.cours.id || null,
        lien: this.data.cours.lien || null,
        dateDebut: new Date(this.data.cours.dateDebut) || null,
        dateFin: new Date(this.data.cours.dateFin) || null,
        matiereUser: this.data.cours.matiereUser || null,
        salle: this.data.cours.salle || null,
        campus: this.data.cours.salle.campus || null,
        filiere: this.data.cours.matiereUser.filiere || null
      });
    }
  }

  ngOnInit(): void {
    this.getAllFiliere();
    this.getAllCampus();
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSaving = true;
    const formValue = this.form.value;

    const coursData = {
      id: formValue.id,
      lien: formValue.lien,
      dateDebut: dayjs(formValue.dateDebut),
      dateFin: dayjs(formValue.dateFin),
      matiereUser: formValue.matiereUser,
      salle: formValue.salle,
      filiere: formValue.filiere
    };

    if (this.data.mode === 'add') {
      this.calendrierCoursService.create([coursData]).subscribe({
        next: () => {
          this.dialogRef.close('saved');
        },
        error: () => {
          this.isSaving = false;
        }
      });
    } else if (this.data.cours) {
      const updatedCours = { ...this.data.cours, ...coursData };
      this.calendrierCoursService.update(updatedCours).subscribe({
        next: () => {
          this.dialogRef.close('saved');
        },
        error: () => {
          this.isSaving = false;
        }
      });
    }
  }

  getAllSalles(nom: string = ""): void {
    this.salleService.query({ page: 0, size: 10, numero: nom, campus: this.selectedCampus  }).subscribe(
      response => {
        this.salles = response.body["data"] || [];
      }
    );
  }

  getAllMatiere(nom: string = ""): void {
    const filiereId = this.selectedFiliere?.nom;
    this.matiereService.query({ 
      page: 0, 
      size: 10, 
      matiere: nom, 
      filiere: filiereId 
    }).subscribe(
      response => {
        this.matieres = response.body["data"] || [];
      }
    );
  }

  getAllFiliere(nom: string = ""): void {
    this.filiereService.query({ page: 0, size: 10, nom: nom, campus: this.selectedCampus }).subscribe(
      response => {
        this.filieres = response.body["data"] || [];
      }
    );
  }

  displayFn(item: any): string {
    return item?.nom || '';
  }

  displayFn2(item: any): string {
    return item?.matiere?.nom || '';
  }

  onCancel(): void {
    this.dialogRef.close();
  }

   displayFn3(item: any){
    return item?.numero || '';
  }

  getAllCampus(nom: any = "") {
    this._campusService.query({ page: 0, size: 10, nom: nom }).subscribe(
      response => {
        this.campuses = response.body["data"];
      }
    )
  }
}