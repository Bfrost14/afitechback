import { ViewChild, AfterViewInit, Component, OnInit, Input } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortable } from '@angular/material/sort';
import { FormArray, UntypedFormBuilder, UntypedFormGroup, FormControl } from '@angular/forms';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
import dayjs from 'dayjs/esm';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TypeNote } from '../../enumerations/type-note.model';
import { NoteService } from '../service/note.service';
import { AnneeScolaireUtilisateurService } from '../../etudiant/annee-scolaire-utilisateur.service';


@Component({
  selector: 'app-ajout-note',
  templateUrl: './ajout-note.component.html',
  styleUrls: ['./ajout-note.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class AjoutNoteComponent implements OnInit, AfterViewInit {
  @Input() typeNote: string;
  @Input() matiereUtilisateurId: number;
  @Input() matiereUser: any;

  // Configuration du tableau
  columnsToDisplay = ['select', 'matricule', 'nom', 'note'];
  displayedColumns: string[] = [...this.columnsToDisplay];
  dataSource = new MatTableDataSource<NewUtilisateur>();
  selection = new SelectionModel<NewUtilisateur>(true, []);

  // États du composant
  hasNotes: boolean = false;
  showSaveButton: boolean = true;

  // Formulaires
  noteForm: UntypedFormGroup;
  notes: FormArray;

  // Pagination et tri
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  pageSize = 25;
  pageSizeOptions: number[] = [75, 100];
  canEditNote: boolean = true;

  constructor(
    private _anneeScolaireUserService: AnneeScolaireUtilisateurService,
    private _noteService: NoteService,
    private _formBuilder: UntypedFormBuilder,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.noteForm = this._formBuilder.group({
      notes: this._formBuilder.array([])
    });
    this.notes = this.noteForm.get('notes') as FormArray;

  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.sort.sort({ id: 'id', start: 'desc' } as MatSortable);
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.loadNotes();
  }


  loadNotes() {
    this.isLoadingResults = true;
    this._noteService.query({
      page: this.paginator?.pageIndex || 0,
      size: 100,
      sort: this.sort?.active + "," + this.sort?.direction,
      idMatiereUser: this.matiereUtilisateurId,
      typeNote: this.typeNote
    }).subscribe(response => {
      if (response.body.data.length > 0) {
        this.hasNotes = true;
        this.showSaveButton = !this.canEditNote;
        this.dataSource.data = response.body.data.map(item => item.user);
        this.createNoteForms(response.body.data);
      } else {
        this.hasNotes = false;
        this.showSaveButton = this.canEditNote;
        this.loadEtudiants();
      }
      this.resultsLength = response.body.pagination.length;
      this.isLoadingResults = false;
    });
  }

  loadEtudiants() {
    this._anneeScolaireUserService.query({
      page: this.paginator?.pageIndex || 0,
      size: 100,
      sort: this.sort?.active + "," + this.sort?.direction,
      filiere: this.matiereUser.filiere.nom, semestre: this.matiereUser.semestre.nom
    }).subscribe(response => {
      this.dataSource.data = response.body.data;
      this.createEmptyNoteForms(response.body.data);
      this.resultsLength = response.body.pagination.length;
      this.isLoadingResults = false;
    });
  }

  createNoteForms(notes: any[]) {
    this.notes.clear();
    notes.forEach(note => {
      this.notes.push(this._formBuilder.group({
        id: [note.id],
        valeur: [{ value: note.valeur, disabled: true }], // ✅ justification modifiable si absent
        matiereUser: [this.matiereUser],
        user: [note.user],
        typeNote: [this.typeNote]
      }));
    });
  }


  createEmptyNoteForms(etudiants: NewUtilisateur[]) {
    this.notes.clear();
    etudiants.forEach(etudiant => {
      this.notes.push(this._formBuilder.group({
        id: [null],
        matiereUser: [this.matiereUser],
        valeur: [null],
        user: [etudiant],
        typeNote: [this.typeNote]
      }));
    });
  }

  saveNotes() {
    if (!this.canEditNote) {
      this._snackBar.open('La période de modification des présences est terminée', 'OK', { duration: 3000 });
      return;
    }

    const notesToSave = this.noteForm.value.notes
      .filter((_, index) => this.selection.isSelected(this.dataSource.data[index]))
      .map(note => ({
        id: note.id,
        valeur: note.valeur,
        matiereUser: this.matiereUser,
        user: note.user.user,
        typeNote: this.typeNote
      }));

    const operation = this.hasNotes
      ? this._noteService.update(notesToSave)
      : this._noteService.create(notesToSave);

    operation.subscribe({
      next: () => {
        this._snackBar.open('Présences enregistrées avec succès', 'OK', { duration: 3000 });
        this.loadNotes();
        this.selection.clear();
      },
      error: (err) => {
        console.error('Erreur lors de la sauvegarde', err);
        this._snackBar.open('Erreur lors de l\'enregistrement des présences', 'OK', { duration: 3000 });
      }
    });
  }

  toggleJustification(index: number) {
    const noteControl = this.notes.at(index).get('note');
    const justificationControl = this.notes.at(index).get('justifie');

    if (noteControl.value) {
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

  onNoteChanged(index: number): void {
    const valeur = this.notes.at(index).get('valeur')?.value;
    const etudiant = this.dataSource.data[index];

    if (valeur !== null && valeur !== undefined && valeur !== '') {
      this.selection.select(etudiant);
    } else {
      this.selection.deselect(etudiant);
    }
  }

}
