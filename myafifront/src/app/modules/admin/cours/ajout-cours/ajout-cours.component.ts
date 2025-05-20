import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewCours } from '../cours.model';
import { CoursService } from '../service/cours.service';
@Component({
    selector: 'app-ajout-cours',
    templateUrl: './ajout-cours.component.html',
    styleUrls: ['./ajout-cours.component.scss'],
})
export class AjoutCoursComponent implements OnInit {
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
    @Input() coursId: number;
    @Input() cours: NewCours;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie cours ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _coursService: CoursService,
        private _alertToastService: AlertToastService
    ) {
       
    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
          id: [null],
          matricule: [null],
          nom: [null, Validators.required],
          prenom: [null, Validators.required],
          email: [null, Validators.required],
          dateDeNaissance: [null, Validators.required],
          role: ["ROLE_ETUDIANT", Validators.required],
          filiere: [null, Validators.required],
          telephone: [null, Validators.required],

      });
        if(this.cours != undefined){
          this.form.patchValue(this.cours)
        }
        
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("cours form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'cours form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._coursService.create(this.form.value).subscribe((data) => {
            console.log(
                'saved cours  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout cours');
        });
    }

    modifier() {

        this._coursService.update(this.form.value).subscribe((data) => {
            console.log(
                'updated cours  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification cours'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter cours @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  cours: [cours],
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
        console.log(
            '>>>>>>>>>> cours to delete >>>>>>>>>>>>>>>>>>',
            this.cours
        );
        // Open the confirmation dialog
        
                // Delete the cours on the server
                this._coursService
                    .delete(this.cours.id)
                    .subscribe(() => {
                        this._alertToastService.toastSuccess(
                            'Réussie',
                            'Suppression étudiant'
                        );
                        this.add.emit(false);
                    });
            }
    
    
}
