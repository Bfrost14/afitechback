import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { AbstractControl, UntypedFormBuilder, UntypedFormGroup, ValidatorFn, Validators } from '@angular/forms';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewPointageProfesseur } from '../pointage-professeur.model';
import { PointageProfesseurService } from '../service/pointage-professeur.service';
import { AdminService } from '../../user/service/admin.service';
import { Dayjs } from 'dayjs';
@Component({
    selector: 'app-ajout-pointage-professeur',
    templateUrl: './ajout-pointage-professeur.component.html',
    styleUrls: ['./ajout-pointage-professeur.component.scss'],
})
export class AjoutPointageProfesseurComponent implements OnInit {
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
    @Input() pointageId: number;
    @Input() pointage: NewPointageProfesseur;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();
    // ++++++++++++++++++++++++++++++++++++ mat-select infinit scroll categorie pointage ++++++++++++++++++++++++++++++++++

    dateToday: string = this.formatDateForInput(new Date());
    genreNgModel: string = '';
    mesLists: any[] = [];
    titeleMessage: string;
    labelMessage: string;
    buttonMessage: string;

    adresse: any[] = [];
    users: any[] = [];
    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _pointageService: PointageProfesseurService,
        private _alertToastService: AlertToastService,
        private _userService: AdminService
    ) {

    }

    ngOnInit(): void {

        this.form = this._formBuilder.group({
            id: [null],
            professeur: [null, Validators.required],
            heureDepart: [null],
            heureArrivee: [null, Validators.required],


        },
            { validators: heureDepartApresArriveeValidator() });
        if (this.pointage != undefined) {
            this.form.patchValue(this.pointage)
        }

        this.getAllProfesseur()

    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    formatDateForInput(date: Date): string {
        const pad = (n: number) => n.toString().padStart(2, '0');
        const yyyy = date.getFullYear();
        const MM = pad(date.getMonth() + 1);
        const dd = pad(date.getDate());
        const hh = pad(date.getHours());
        const mm = pad(date.getMinutes());
        return `${yyyy}-${MM}-${dd}T${hh}:${mm}`;
    }

    formatDateForInputDayJs(date: Dayjs): string {
        const pad = (n: number) => n.toString().padStart(2, '0');
        const yyyy = date.year();
        const MM = pad(date.month() + 1);
        const dd = pad(date.date());
        const hh = pad(date.hour());
        const mm = pad(date.minute());
        return `${yyyy}-${MM}-${dd}T${hh}:${mm}`;
    }

    enregistrer() {
        //  console.log("pointage form >>>>>>>>>>>>>>>>>>>>>>>>>>>>",this.form.value)

        console.log(
            'pointage form >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
            this.form.value
        );


        this._pointageService.create(this.preparePayload()).subscribe((data) => {
            console.log(
                'saved pointage  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout pointage');
        });
    }

    preparePayload(): NewPointageProfesseur {
        const raw = this.form.value;
        return {
            ...raw,
            heureArrivee: new Date(raw.heureArrivee).toISOString(), // "2025-05-21T13:19:00.000Z"
            heureDepart: new Date(raw.heureDepart).toISOString(),
        };
    }

    preparePayloadUpdate(): any {
        const raw = this.form.value;
        const heureArrivee = new Date(raw.heureArrivee);
        const heureDepart = new Date(raw.heureDepart);

        if (heureDepart < heureArrivee) {
            return this._alertToastService.toastDanger("L'heure de départ ne peut pas être antérieure à l'heure d'arrivée.", "Erreur");
        }

        return {
            ...raw,
            heureArrivee: heureArrivee.toISOString(),
            heureDepart: heureDepart.toISOString(),
        };
    }




    modifier() {

        this._pointageService.update(this.preparePayloadUpdate()).subscribe((data) => {
            console.log(
                'updated pointage  >>>>>>>>>>>>>>>>>>>>>>>>>>>>',
                data.body
            );
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification pointage'
            );
        });
    }

    annuler() {
        console.log('@@@@@@@@@@ annuler ajouter pointage @@@@@@@@@@@@@');
        this.add.emit(false);
        // this.edit.emit(false)
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            //  pointage: [pointage],
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
            '>>>>>>>>>> pointage to delete >>>>>>>>>>>>>>>>>>',
            this.pointage
        );
        // Open the confirmation dialog

        // Delete the pointage on the server
        this._pointageService
            .delete(this.pointage.id)
            .subscribe(() => {
                this._alertToastService.toastSuccess(
                    'Réussie',
                    'Suppression étudiant'
                );
                this.add.emit(false);
            });
    }

    displayFn2(user: any): string {
        return user && user.firstName ? user.firstName + ' ' + user.lastName : '';
    }


    getAllProfesseur(email: any = "") {
        this._userService.query({ page: 0, size: 10, email: email, profil: 'PROFESSEUR' }).subscribe(
            response => {
                this.users = response.body["data"];
            }
        )
    }
}

export function heureDepartApresArriveeValidator(): ValidatorFn {
    return (group: AbstractControl): { [key: string]: any } | null => {
        const arrivee = new Date(group.get('heureArrivee')?.value);
        const depart = new Date(group.get('heureDepart')?.value);
        if (depart.getFullYear() != 1970) {
            if (arrivee && depart && depart < arrivee) {
                return { heureDepartInvalide: true };
            }
        }


        return null;
    };
}