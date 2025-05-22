import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewAnneeScolaire } from '../annee-scolaire.model';
import { AnneeScolaireService } from '../service/annee-scolaire.service';
@Component({
    selector: 'app-ajout-annee-scolaire',
    templateUrl: './ajout-annee-scolaire.component.html',
    styleUrls: ['./ajout-annee-scolaire.component.scss'],
})
export class AjoutAnneeScolaireComponent implements OnInit {
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
    @Input() anneeScolaireId: number;
    @Input() anneeScolaire: NewAnneeScolaire;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie anneeScolaire ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _anneeScolaireService: AnneeScolaireService,
        private _alertToastService: AlertToastService
    ) {
       
    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
          id: [null],
          nom: [null, Validators.required],

      });
        if(this.anneeScolaire != undefined){
          this.form.patchValue(this.anneeScolaire)
        }
        
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("anneeScolaire form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'anneeScolaire form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._anneeScolaireService.create(this.form.value).subscribe((data) => {
            console.log(
                'saved anneeScolaire  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout anneeScolaire');
        });
    }

    modifier() {

        this._anneeScolaireService.update(this.form.value).subscribe((data) => {
            console.log(
                'updated anneeScolaire  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification anneeScolaire'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter anneeScolaire @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  anneeScolaire: [anneeScolaire],
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
            '>>>>>>>>>> anneeScolaire to delete >>>>>>>>>>>>>>>>>>',
            this.anneeScolaire
        );
        // Open the confirmation dialog
        
                // Delete the anneeScolaire on the server
                this._anneeScolaireService
                    .delete(this.anneeScolaire.id)
                    .subscribe(() => {
                        this._alertToastService.toastSuccess(
                            'Réussie',
                            'Suppression étudiant'
                        );
                        this.add.emit(false);
                    });
            }
    
    
}
