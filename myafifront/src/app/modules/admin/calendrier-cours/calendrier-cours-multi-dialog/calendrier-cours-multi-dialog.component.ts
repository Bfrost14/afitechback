import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import dayjs from 'dayjs/esm';
import { CalendrierCoursService } from '../service/calendrier-cours.service';
import { SalleService } from '../../salle/service/salle.service';
import { MatiereUtilisateurService } from '../../matiere-utilisateur/service/matiere-utilisateur.service';
import { FiliereService } from '../../filiere/service/filiere.service';
import { CampusService } from '../../campus/service/campus.service';

@Component({
  selector: 'app-calendrier-cours-multi-dialog',
  templateUrl: './calendrier-cours-multi-dialog.component.html',
  styleUrls: ['./calendrier-cours-multi-dialog.component.scss']
})
export class CalendrierCoursMultiDialogComponent implements OnInit {
  form: FormGroup;
  isSaving = false;
  filieres: any[] = [];
  matieres: any[] = [];
  salles: any[] = [];
  selectedFiliere: any;
  campuses:any[] = [];
  selectedCampus: any;

  constructor(
    public dialogRef: MatDialogRef<CalendrierCoursMultiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private calendrierCoursService: CalendrierCoursService,
    private filiereService: FiliereService,
    private matiereService: MatiereUtilisateurService,
    private salleService: SalleService,
    private _campusService: CampusService
  ) {
    this.form = this.fb.group({
      coursArray: this.fb.array([this.createCoursFormGroup()])
    });
  }

  ngOnInit(): void {
    this.getAllFiliere();
    this.getAllCampus();
  }

  get coursArray(): FormArray {
    return this.form.get('coursArray') as FormArray;
  }

  createCoursFormGroup(): FormGroup {
    return this.fb.group({
      dateDebut: [null, Validators.required],
      dateFin: [null, Validators.required],
      matiereUser: [null, Validators.required],
      salle: [null, Validators.required],
      filiere: [null, Validators.required],
      campus: [null, Validators.required],
      lien: [null]
    });
  }

  addCours(): void {
    this.coursArray.push(this.createCoursFormGroup());
  }

  removeCours(index: number): void {
    this.coursArray.removeAt(index);
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSaving = true;
    const coursData = this.coursArray.value.map((item: any) => ({
      id: null,
      lien: item.lien,
      dateDebut: dayjs(item.dateDebut),
      dateFin: dayjs(item.dateFin),
      matiereUser: item.matiereUser ,
      salle:  item.salle ,
      filiere: { id: item.filiere }
    }));

    this.calendrierCoursService.create(coursData).subscribe({
      next: () => {
        this.dialogRef.close('saved');
      },
      error: () => {
        this.isSaving = false;
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  getAllSalles(nom: string = ""): void {
    this.salleService.query({ page: 0, size: 10, numero:nom, campus: this.selectedCampus }).subscribe(
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
    this.filiereService.query({ page: 0, size: 10, nom }).subscribe(
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