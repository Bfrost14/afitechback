import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormArray, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { NewAnneeScolaireUtilisateur } from '../../annee-scolaire-utilisateur.model';
import { AnneeScolaireUtilisateurService } from '../../annee-scolaire-utilisateur.service';
import { AnneeScolaireService } from 'app/modules/admin/annee-scolaire/service/annee-scolaire.service';
import { NewUtilisateur } from 'app/modules/admin/ue/ue.model';
import { SemestreService } from 'app/modules/admin/semestre/service/semestre.service';
@Component({
    selector: 'app-ajout-annee-scolaire-utilisateur',
    templateUrl: './ajout-annee-scolaire-utilisateur.component.html',
    styleUrls: ['./ajout-annee-scolaire-utilisateur.component.scss'],
})
export class AjoutAnneeScolaireUtilisateurComponent implements OnInit {
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
    @Input() anneeScolaireUtilisateurId: number;
    @Input() anneeScolaireUtilisateur: NewAnneeScolaireUtilisateur;
    @Input() user: NewUtilisateur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie anneeScolaire ++++++++++++++++++++++++++++++++++
    detailFilds: FormArray;
    adresse: any[] = [];
    index: number = 1;
    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;


    filieres: any[] = [];
    anneeScolaires: any[] = [];
    semestres: any[] = [];
    users: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _anneeScolaireUtilisateurService: AnneeScolaireUtilisateurService,
        private _alertToastService: AlertToastService,
        private _anneeScolaireService: AnneeScolaireService,
        private _semestreService: SemestreService,

    ) {

    }

    ngOnInit(): void {

         this.form = this._formBuilder.group({
            anneeScolaires: this._formBuilder.array([]),
        });
        this.detailFilds = this.form.get('anneeScolaires') as FormArray;
       
        if (this.anneeScolaireUtilisateur != undefined) {
            this.addFieldDetail(this.anneeScolaireUtilisateur);
        } else {
            this.addFieldDetail();
        }
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
        this._anneeScolaireUtilisateurService.create(this.detailFilds.getRawValue()).subscribe((data) => {
            console.log(
                'saved affectation  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Affectation reussi');
        });
    }

    modifier() {

        this._anneeScolaireUtilisateurService.update(this.detailFilds.getRawValue()[0]).subscribe((data) => {
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
            this.anneeScolaireUtilisateur
        );
        // Open the confirmation dialog

        // Delete the anneeScolaire on the server
        this._anneeScolaireUtilisateurService
            .delete(this.anneeScolaireUtilisateur.id)
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
    

    getAllAnneeScolaire(nom: any = "") {
        this._anneeScolaireService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.anneeScolaires = response.body["data"];
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



    addFieldDetail(anneeScolaireUtilisateur?: NewAnneeScolaireUtilisateur) {
        this.index = this.index + 1;
        if (anneeScolaireUtilisateur != null && anneeScolaireUtilisateur != undefined) {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [anneeScolaireUtilisateur.id],
                    anneeScolaire: [anneeScolaireUtilisateur.anneeScolaire, [Validators.required]],
                    user: [this.user, [Validators.required]],
                    semestre: [anneeScolaireUtilisateur.semestre, [Validators.required]],
                })
            );
        } else {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [null],
                    anneeScolaire: [null, Validators.required],
                    semestre: [null, Validators.required],
                    user: [this.user, Validators.required],
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
