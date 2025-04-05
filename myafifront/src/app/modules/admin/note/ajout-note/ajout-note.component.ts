import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import {
    FormArray,
    UntypedFormBuilder,
    UntypedFormGroup,
    Validators,
} from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { Note } from '../note';
import { NoteService } from '../note.service';
import { EtudiantService } from '../../etudiant/etudiant.service';
import { UserAll } from '../../etudiant/user-all';
@Component({
    selector: 'app-ajout-note',
    templateUrl: './ajout-note.component.html',
    styleUrls: ['./ajout-note.component.css'],
})
export class AjoutNoteComponent implements OnInit {
    Enregistrer: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Enregistrer !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    Annuler: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Annuler !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
        reverseButtons: true,
    };

    Modifier: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Modifier !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    Supprimer: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Supprimer !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    form: UntypedFormGroup;
    @Input() noteId: number;
    @Input() note: Note;
    @Input() user: UserAll;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie note ++++++++++++++++++++++++++++++++++
    users: UserAll[] = []
    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;
    detailFilds: FormArray;
    adresse: any[] = [];
    index: number = 1;
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _noteService: NoteService,
        private _alertToastService: AlertToastService,
        private _etudiantService: EtudiantService
    ) {}

    ngOnInit(): void {
        this.form = this._formBuilder.group({
            notes: this._formBuilder.array([]),
        });
        this.detailFilds = this.form.get('notes') as FormArray;
        console.log(this.user)
        if (this.note != undefined) {
            this.addFieldDetail(this.note);
        } else {
            this.addFieldDetail();
        }

        this.getUsers()
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("note form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log('note form >>>>>>>>>>>>>>>>>>>>>>>>>>>>', this.form.value);

        this.form.value.password = environment.defaultPassword;
        this._noteService.saveallnote(this.detailFilds.getRawValue()).subscribe((data) => {
            console.log('saved note  >>>>>>>>>>>>>>>>>>>>>>>>>>>>', data.body);
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout note');
        });
    }

    modifier() {
        this._noteService
            .updatenote(this.detailFilds.getRawValue()[0])
            .subscribe((data) => {
                console.log(
                    'updated note  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                    data.body
                );
                this.add.emit(false);
                this._alertToastService.toastSuccess(
                    'Réussie',
                    'Modification note'
                );
            });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter note @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  note: [note],
            adresse: [valeur],
        });
    }

    selectionChange(indice) {
        // console.log("form value ==========>",this.horizontalStepperForm.get('step1').value)

        if (indice >= 0) {
            /*   this.getAdresseByParent(this.adresse[indice].id,indice+1)
             */
        }
    }
    supprimer() {
        console.log('>>>>>>>>>> note to delete >>>>>>>>>>>>>>>>>>', this.note);
        // Open the confirmation dialog

        // Delete the note on the server
        this._noteService.deletenote(this.note.id).subscribe(() => {
            this._alertToastService.toastSuccess(
                'Réussie',
                'Suppression étudiant'
            );
            this.add.emit(false);
        });
    }

    addFieldDetail(note?: Note) {
        this.index = this.index + 1;
        if (note != null && note != undefined) {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [note.id],
                    semestre: [note.semestre, [Validators.required]],
                    matiere: [note.matiere, [Validators.required]],
                    valeur: [note.valeur, [Validators.required]],
                    credit: [note.credit],
                    user: [note.user],
                })
            );
        } else {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [null],
                    semestre: [null, [Validators.required]],
                    matiere: [null, [Validators.required]],
                    valeur: [null, [Validators.required]],
                    credit: [null],
                    user: [this.user != undefined ? this.user : null],
                })
            );
        }
    }

    removeFieldDetail(niveau: number) {
        for (let i = this.index; i >= niveau; i--) {
            (this.detailFilds as FormArray).removeAt(i);
            this.index = this.index - 1;
        }
        if (this.index == 0) {
            this.index = 1;
        }
    }

    onKeyDownDetail(event: KeyboardEvent): void {
        if (event.key === 'Tab') {
            // Tab key was pressed, do something
            this.addFieldDetail();
        }
    }

    displayFn(user: any): string {
      return user && user.matricule ? user.matricule + " " + user.prenom + ' ' + user.nom : '';
  }

  getUsers(nom: string = '') {
    this._etudiantService
        .getuser(0, 20, 'id', 'asc', '', "","",nom)
        .subscribe((data) => {
            this.users = data.body['data'];
           
        });
}
}
