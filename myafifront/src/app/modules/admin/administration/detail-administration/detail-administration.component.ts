import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
import { AccountService } from 'app/core/auth/account.service';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { SweetAlertOptions } from 'sweetalert2';

@Component({
    selector: 'app-detail-administration',
    templateUrl: './detail-administration.component.html',
    styleUrls: ['./detail-administration.component.scss'],
    animations: fuseAnimations,
})
export class DetailAdministrationComponent implements OnInit {
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


    Supprimer: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Restaurer le compte !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() administrationId: string = "";
    administration: any;
    fiche: boolean = true;

    constructor(
        private _administrationService: AdminService,
        private _accountService: AccountService,
        private alert: AlertToastService
    ) { }

    ngOnInit(): void {

        this.getAdministration();

    }

    getAdministration() {
        return this._administrationService
            .get(this.administrationId)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ administration one  @@@@@@@@@@@@',
                    data
                );
                this.administration = data;
            });
    }



    refresh(emp: NewUtilisateur) {
        this.administration = emp;
    }

    activation() {
        return this._accountService
            .activate(this.administrationId)
            .subscribe((data) => {
                this.alert.toastSuccess("Opération réussi", "Success")
                this.getAdministration()
            });
    }

    resetPassword() {
        return this._accountService
            .resetPassword(this.administrationId)
            .subscribe((data) => {
                this.alert.toastSuccess("Mot de passe réinitialisé", "Success")
                this.getAdministration()
            });
    }

}
