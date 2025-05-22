import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormArray, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { UEService } from '../../ue/service/ue.service';
import { NewMatiereUtilisateur } from '../matiere-utilisateur.model';
import { MatiereUtilisateurService } from '../service/matiere-utilisateur.service';
import { FiliereService } from '../../filiere/service/filiere.service';
import { MatiereService } from '../../matiere/service/matiere.service';
import { UserService } from '../../user/service/user.service';
import { AdminService } from '../../user/service/admin.service';
import { AnneeScolaireService } from '../../annee-scolaire/service/annee-scolaire.service';
import { SemestreService } from '../../semestre/service/semestre.service';
@Component({
    selector: 'app-ajout-matiere-utilisateur',
    templateUrl: './ajout-matiere-utilisateur.component.html',
    styleUrls: ['./ajout-matiere-utilisateur.component.scss'],
})
export class AjoutMatiereUtilisateurComponent implements OnInit {
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
    @Input() matiereUtilisateurId: number;
    @Input() matiereUtilisateur: NewMatiereUtilisateur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie matiere ++++++++++++++++++++++++++++++++++
    detailFilds: FormArray;
    adresse: any[] = [];
    index: number = 1;
    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;


    filieres: any[] = [];
    matieres: any[] = [];
    users: any[] = [];
    anneeScolaires: any[] = [];
    semestres: any[] = []
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _matiereService: MatiereUtilisateurService,
        private _alertToastService: AlertToastService,
        private _filiereService: FiliereService,
        private _matService: MatiereService,
        private _userService: AdminService,
        private _anneeScolaireService: AnneeScolaireService,
        private _semestreService: SemestreService

    ) {

    }

    ngOnInit(): void {

         this.form = this._formBuilder.group({
            matieres: this._formBuilder.array([]),
        });
        this.detailFilds = this.form.get('matieres') as FormArray;
       
        if (this.matiereUtilisateur != undefined) {
            this.addFieldDetail(this.matiereUtilisateur);
        } else {
            this.addFieldDetail();
        }

        this.getAllFiliere()
        this.getAllProfesseur()
        this.getAllMatiere()
        this.getAllAnneeScolaire()
        this.getAllSemestre()
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("affectation form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'affectation form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );
        this._matiereService.create(this.detailFilds.getRawValue()).subscribe((data) => {
            console.log(
                'saved affectation  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Affectation reussi');
        });
    }

    modifier() {

        this._matiereService.update(this.detailFilds.getRawValue()[0]).subscribe((data) => {
            console.log(
                'updated affectation  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification affectation'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter matiere @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  matiere: [matiere],
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
            '>>>>>>>>>> matiere to delete >>>>>>>>>>>>>>>>>>',
            this.matiereUtilisateur
        );
        // Open the confirmation dialog

        // Delete the matiere on the server
        this._matiereService
            .delete(this.matiereUtilisateur.id)
            .subscribe(() => {
                this._alertToastService.toastSuccess(
                    'Réussie',
                    'Suppression matière'
                );
                this.add.emit(false);
            });
    }

    displayFn(ue: any): string {
        return ue && ue.nom ? ue.nom : '';
    }

    displayFn2(user: any): string {
        return user && user.firstName ? user.firstName+' '+user.lastName : '';
    }

    getAllAnneeScolaire(email: any = "") {
        this._anneeScolaireService.query({ page: 0, size: 10, email: email, profil: 'PROFESSEUR' }).subscribe(
            response => {
                this.anneeScolaires = response.body["data"];
            }
        )
    }

    getAllProfesseur(email: any = "") {
        this._userService.query({ page: 0, size: 10, email: email, profil: 'PROFESSEUR' }).subscribe(
            response => {
                this.users = response.body["data"];
            }
        )
    }

    getAllMatiere(nom: any = "") {
        this._matService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.matieres = response.body["data"];
            }
        )
    }

    getAllFiliere(nom: any = "") {
        this._filiereService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.filieres = response.body["data"];
            }
        )
    }

    getAllSemestre(nom: any = "") {
        this._semestreService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.semestres = response.body["data"];
            }
        )
    }


    addFieldDetail(matiereUtilisateur?: NewMatiereUtilisateur) {
        this.index = this.index + 1;
        if (matiereUtilisateur != null && matiereUtilisateur != undefined) {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [matiereUtilisateur.id],
                    anneeScolaire: [matiereUtilisateur.anneeScolaire, [Validators.required]],
                    filiere: [matiereUtilisateur.filiere, [Validators.required]],
                    matiere: [matiereUtilisateur.matiere, [Validators.required]],
                    user: [matiereUtilisateur.user, [Validators.required]],
                    semestre: [matiereUtilisateur.semestre, [Validators.required]],
                })
            );
        } else {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [null],
                    matiere: [null, Validators.required],
                    anneeScolaire: [null, Validators.required],
                    user: [null, Validators.required],
                    filiere: [null, Validators.required],
                    semestre: [null, Validators.required],
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
}
