import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewSalle } from '../salle.model';
import { SalleService } from '../service/salle.service';
import { CampusService } from '../../campus/service/campus.service';
import { NewCampus } from '../../campus/campus.model';
@Component({
    selector: 'app-ajout-salle',
    templateUrl: './ajout-salle.component.html',
    styleUrls: ['./ajout-salle.component.scss'],
})
export class AjoutSalleComponent implements OnInit {
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
    @Input() salleId: number;
    @Input() salle: NewSalle;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie salle ++++++++++++++++++++++++++++++++++

    campus: NewCampus[] = []
    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _salleService: SalleService,
        private _alertToastService: AlertToastService,
        private _campusService: CampusService
    ) {

    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
            id: [null],
            numero: [null, Validators.required],
            campus: [null, Validators.required],

        });
        if (this.salle != undefined) {
            this.form.patchValue(this.salle)
        }

        this.getAllCampus()

    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        //  console.log("salle form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'salle form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );

        this.form.value.password = environment.defaultPassword
        this._salleService.create(this.form.value).subscribe((data) => {
            console.log(
                'saved salle  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout salle');
        });
    }

    modifier() {

        this._salleService.update(this.form.value).subscribe((data) => {
            console.log(
                'updated salle  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification salle'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter salle @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  salle: [salle],
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
            '>>>>>>>>>> salle to delete >>>>>>>>>>>>>>>>>>',
            this.salle
        );
        // Open the confirmation dialog

        // Delete the salle on the server
        this._salleService
            .delete(this.salle.id)
            .subscribe(() => {
                this._alertToastService.toastSuccess(
                    'Réussie',
                    'Suppression salle'
                );
                this.add.emit(false);
            });
    }

    getAllCampus(nom: string = ""){
        this._campusService.query({page: 0, size: 10, nom: nom}).subscribe(
            response => {
                this.campus = response.body["data"];
            }
        )
    }

    displayFn(campus: any): string {
      return campus && campus.nom ? campus.nom : '';
    }


}
