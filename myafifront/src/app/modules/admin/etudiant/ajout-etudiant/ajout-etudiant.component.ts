import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
import { AuthService } from 'app/core/auth/auth.service';
import { CampusService } from '../../campus/service/campus.service';
import { FiliereService } from '../../filiere/service/filiere.service';
import { ProfileService } from '../../profile/service/profile.service';
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
    @Input() etudiantId: string;
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
    filieres: any[] = [];
    campus: any[] = [];
    profiles: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _etudiantService: AdminService,
        private _alertToastService: AlertToastService,
        private _authService: AuthService,
        private _campusService: CampusService,
        private _filiereService: FiliereService,
        private _profileService: ProfileService
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
            login: [null],
            profil: [null],
            campus: [null, Validators.required],
            nationalite: [null, Validators.required],
            campuses: [[]],
            filiere: [null, Validators.required],
            dateDeNaissance: [null, Validators.required],
            authorities: [null],

        });
        if (this.etudiant != undefined) {
            this.form.patchValue(this.etudiant)
        }

        this.getAllCampus()
        this.getAllFiliere("")
        this.getAllProfile()

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
        this.form.value.login = this.form.value.email
        let profile = this.profiles.find(profile => profile.nom == "ETUDIANT")
        if (profile == undefined) {
            return this._alertToastService.toastDanger('Profil étudiant inéxistant', 'Profile non trouvé')
        }
        this.form.value.profil = profile
        this.form.value.authorities = profile.authorities
        this.form.value.langKey = 'fr'
        this.form.value.password = environment.defaultPassword

        this.form.value.campuses = []
        this.form.value.campuses.push(this.form.value.campus)
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
         let profile = this.profiles.find(profile => profile.nom == "ETUDIANT")
        if (profile == undefined) {
            return this._alertToastService.toastDanger('Profil étudiant inéxistant', 'Profile non trouvé')
        }
        this.form.value.profil = profile
        this.form.value.authorities = profile.authorities
        this._etudiantService.updateuser(this.etudiantId, this.form.value).subscribe((data) => {
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

    getAllFiliere(nom: string) {
        this._filiereService.query({ page: 0, size: 10, sort: 'id,desc', nom: nom }).subscribe(data => {
            this.filieres = data.body.data;
        })
    }

    displayFn(profil: any): string {
        return profil && profil.nom ? profil.nom : '';
    }

    getAllCampus(nom: string = "") {
        this._campusService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.campus = response.body["data"];
            }
        )
    }

     getAllProfile(nom: string = "") {
        this._profileService.query({page: 0, size: 10, sort: 'id,desc', typeProfil: "ETUDIANT", nom: nom}).subscribe(data =>{
            this.profiles = data.body.data;
        })
    }

}
