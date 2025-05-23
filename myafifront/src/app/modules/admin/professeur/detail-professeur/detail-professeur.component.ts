import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { AccountService } from 'app/core/auth/account.service';
import { SweetAlertOptions } from 'sweetalert2';

@Component({
    selector: 'app-detail-professeur',
    templateUrl: './detail-professeur.component.html',
    styleUrls: ['./detail-professeur.component.scss'],
    animations: fuseAnimations,
})
export class DetailProfesseurComponent implements OnInit {
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
    @Input() professeurId: string = "";
    professeur: any;
    fiche: boolean = true;

    constructor(
        private _professeurService: AdminService,
        private _accountService: AccountService,
        private alert: AlertToastService
    ) { }

    ngOnInit(): void {

        this.getProfesseur();

    }

    getProfesseur() {
        return this._professeurService
            .get(this.professeurId)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ professeur one  @@@@@@@@@@@@',
                    data
                );
                this.professeur = data;
            });
    }



    refresh(emp: NewUtilisateur) {
        this.professeur = emp;
    }

    activation() {
        return this._accountService
            .activate(this.professeurId)
            .subscribe((data) => {
                this.alert.toastSuccess("Opération réussi", "Success")
                this.getProfesseur()
            });
    }

    resetPassword() {
        return this._accountService
            .resetPassword(this.professeurId)
            .subscribe((data) => {
                this.alert.toastSuccess("Mot de passe réinitialisé", "Success")
                this.getProfesseur()
            });
    }

}
