import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewUtilisateur } from '../../ue/ue.model';
import { UserService } from '../../user/service/user.service';
import { AdminService } from '../../user/service/admin.service';
@Component({
    selector: 'app-ajout-administration',
    templateUrl: './ajout-administration.component.html',
    styleUrls: ['./ajout-administration.component.scss'],
})
export class AjoutAdministrationComponent implements OnInit {
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
    @Input() administrationId: number;
    @Input() administration: NewUtilisateur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie administration ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _administrationService: AdminService,
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
        if(this.administration != undefined){
          this.form.patchValue(this.administration)
        }
        
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("administration form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'administration form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._administrationService.saveuser(this.form.value).subscribe((data) => {
            console.log(
                'saved administration  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout administration');
        });
    }

    modifier() {

        this._administrationService.updateuser(this.administrationId,this.form.value).subscribe((data) => {
            console.log(
                'updated administration  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification administration'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter administration @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  administration: [administration],
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
            '>>>>>>>>>> administration to delete >>>>>>>>>>>>>>>>>>',
            this.administration
        );
        // Open the confirmation dialog
        
                // Delete the administration on the server
                this._administrationService
                    .deleteuser(this.administration.id)
                    .subscribe(() => {
                        this._alertToastService.toastSuccess(
                            'Réussie',
                            'Suppression étudiant'
                        );
                        this.add.emit(false);
                    });
            }
    
    
}
