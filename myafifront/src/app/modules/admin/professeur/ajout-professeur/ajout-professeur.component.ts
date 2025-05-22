import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { AdminService } from '../../user/service/admin.service';
import { NewUtilisateur } from '../../ue/ue.model';
import { ProfileService } from '../../profile/service/profile.service';
import { create } from 'lodash';
import { AuthService } from 'app/core/auth/auth.service';
@Component({
    selector: 'app-ajout-professeur',
    templateUrl: './ajout-professeur.component.html',
    styleUrls: ['./ajout-professeur.component.scss'],
})
export class AjoutProfesseurComponent implements OnInit {
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
    @Input() professeurId: string;
    @Input() professeur: NewUtilisateur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie professeur ++++++++++++++++++++++++++++++++++

    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    profiles: any;
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _professeurService: AdminService,
        private _alertToastService: AlertToastService,
        private _profileService: ProfileService,
        private _authService: AuthService
    ) {

    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
            id: [null],
            matricule: [null],
            firstName: [null, Validators.required],
            lastName: [null, Validators.required],
            email: [null, Validators.required],
            telephone: [null, Validators.required],
            createdBy: [this._authService.getUtilisateur().email],
            login: [null, Validators.required],
            profil:[null],
            authorities: [null],
        });
        if (this.professeur != undefined) {
            this.form.patchValue(this.professeur)
        }

        this.getAllProfile()

    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("professeur form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'professeur form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );
        this.form.value.login = this.form.value.email
        let profile = this.profiles.find(profile => profile.nom == "PROFESSEUR")
        if(profile == undefined){
            return this._alertToastService.toastDanger('Profil professeur inéxistant','Profile non trouvé')
        }
        this.form.value.profil = profile
        this.form.value.authorities = profile.authorities
        this.form.value.langKey = 'fr'
        this.form.value.password = environment.defaultPassword
        this._professeurService.saveuser(this.form.value).subscribe((data) => {
            console.log(
                'saved professeur  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout professeur');
        });
    }

    modifier() {
        this.form.value.lastModifiedBy = this._authService.getUtilisateur().email
        this._professeurService.updateuser(this.professeurId, this.form.value).subscribe((data) => {
            console.log(
                'updated professeur  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification professeur'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter professeur @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  professeur: [professeur],
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
            '>>>>>>>>>> professeur to delete >>>>>>>>>>>>>>>>>>',
            this.professeur
        );
        // Open the confirmation dialog

        // Delete the professeur on the server
        this._professeurService
            .deleteuser(this.professeur.id)
            .subscribe(() => {
                this._alertToastService.toastSuccess(
                    'Réussie',
                    'Suppression professeur'
                );
                this.add.emit(false);
            });
    }

    getAllProfile(){
        this._profileService.query({page: 0, size: 10, sort: 'id,desc', nom: "PROFESSEUR"}).subscribe(data =>{
            this.profiles = data.body.data;
        })
    }

}
