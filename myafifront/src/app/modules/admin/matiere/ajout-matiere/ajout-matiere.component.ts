import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormArray, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewMatiere } from '../matiere.model';
import { MatiereService } from '../service/matiere.service';
import { UEService } from '../../ue/service/ue.service';
@Component({
    selector: 'app-ajout-matiere',
    templateUrl: './ajout-matiere.component.html',
    styleUrls: ['./ajout-matiere.component.scss'],
})
export class AjoutMatiereComponent implements OnInit {
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
    @Input() matiereId: number;
    @Input() matiere: NewMatiere;
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


    ue: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _matiereService: MatiereService,
        private _alertToastService: AlertToastService,
        private _ueService: UEService
    ) {

    }

    ngOnInit(): void {

         this.form = this._formBuilder.group({
            matieres: this._formBuilder.array([]),
        });
        this.detailFilds = this.form.get('matieres') as FormArray;
       
        if (this.matiere != undefined) {
            this.addFieldDetail(this.matiere);
        } else {
            this.addFieldDetail();
        }

        this.getAllUe()
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("matiere form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'matiere form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );
        this._matiereService.create(this.detailFilds.getRawValue()).subscribe((data) => {
            console.log(
                'saved matiere  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout matiere');
        });
    }

    modifier() {

        this._matiereService.update(this.detailFilds.getRawValue()[0]).subscribe((data) => {
            console.log(
                'updated matiere  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification matiere'
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
            this.matiere
        );
        // Open the confirmation dialog

        // Delete the matiere on the server
        this._matiereService
            .delete(this.matiere.id)
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

    getAllUe(nom: any = "") {
        this._ueService.query({ page: 0, size: 10, nom: nom }).subscribe(
            response => {
                this.ue = response.body["data"];
            }
        )
    }


    addFieldDetail(matiere?: NewMatiere) {
        this.index = this.index + 1;
        if (matiere != null && matiere != undefined) {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [matiere.id],
                    nom: [matiere.nom, [Validators.required]],
                    credit: [matiere.credit, [Validators.required]],
                    ue: [matiere.ue, [Validators.required]],
                })
            );
        } else {
            (this.detailFilds as FormArray).push(
                this._formBuilder.group({
                    id: [null],
                    nom: [null, Validators.required],
                    credit: [null, Validators.required],
                    ue: [null, Validators.required],
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
