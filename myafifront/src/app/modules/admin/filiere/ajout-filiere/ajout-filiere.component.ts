import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewFiliere } from '../filiere.model';
import { FiliereService } from '../service/filiere.service';
@Component({
    selector: 'app-ajout-filiere',
    templateUrl: './ajout-filiere.component.html',
    styleUrls: ['./ajout-filiere.component.scss'],
})
export class AjoutFiliereComponent implements OnInit {
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
    @Input() filiereId: number;
    @Input() filiere: NewFiliere;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie filiere ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _filiereService: FiliereService,
        private _alertToastService: AlertToastService
    ) {
       
    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
          id: [null],
          nom: [null, Validators.required],

      });
        if(this.filiere != undefined){
          this.form.patchValue(this.filiere)
        }
        
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("filiere form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'filiere form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._filiereService.create(this.form.value).subscribe((data) => {
            console.log(
                'saved filiere  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout filiere');
        });
    }

    modifier() {

        this._filiereService.update(this.form.value).subscribe((data) => {
            console.log(
                'updated filiere  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification filiere'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter filiere @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  filiere: [filiere],
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
            '>>>>>>>>>> filiere to delete >>>>>>>>>>>>>>>>>>',
            this.filiere
        );
        // Open the confirmation dialog
        
                // Delete the filiere on the server
                this._filiereService
                    .delete(this.filiere.id)
                    .subscribe(() => {
                        this._alertToastService.toastSuccess(
                            'Réussie',
                            'Suppression étudiant'
                        );
                        this.add.emit(false);
                    });
            }
    
    
}
