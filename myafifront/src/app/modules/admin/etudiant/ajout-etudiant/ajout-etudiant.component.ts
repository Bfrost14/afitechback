import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
@Component({
    selector: 'app-ajout-etudiant',
    templateUrl: './ajout-etudiant.component.html',
    styleUrls: ['./ajout-etudiant.component.scss'],
})
export class AjoutEtudiantComponent implements OnInit {
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
    @Input() etudiantId: number;
    @Input() etudiant: NewUtilisateur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie etudiant ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _etudiantService: AdminService,
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
        if(this.etudiant != undefined){
          this.form.patchValue(this.etudiant)
        }
        
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("etudiant form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'etudiant form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._etudiantService.saveuser(this.form.value).subscribe((data) => {
            console.log(
                'saved etudiant  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout etudiant');
        });
    }

    modifier() {

        this._etudiantService.updateuser(this.etudiantId,this.form.value).subscribe((data) => {
            console.log(
                'updated etudiant  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification etudiant'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter etudiant @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  etudiant: [etudiant],
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
            '>>>>>>>>>> etudiant to delete >>>>>>>>>>>>>>>>>>',
            this.etudiant
        );
        // Open the confirmation dialog
        
                // Delete the etudiant on the server
                this._etudiantService
                    .deleteuser(this.etudiant.id)
                    .subscribe(() => {
                        this._alertToastService.toastSuccess(
                            'Réussie',
                            'Suppression étudiant'
                        );
                        this.add.emit(false);
                    });
            }
    
    
}
