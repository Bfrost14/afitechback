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
    selector: 'app-detail-etudiant',
    templateUrl: './detail-etudiant.component.html',
    styleUrls: ['./detail-etudiant.component.scss'],
    animations: fuseAnimations,
})
export class DetailEtudiantComponent implements OnInit {
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
    @Input() etudiantId: string;
    etudiant: any;
    fiche: boolean = true;

    constructor(
        private _etudiantService: AdminService,
        private _accountService: AccountService,
        private alert: AlertToastService
    ) {}

    ngOnInit(): void {
        
    this.getEtudiant();

    }

    getEtudiant() {
        return this._etudiantService
            .get(this.etudiantId)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ etudiant one  @@@@@@@@@@@@',
                    data
                );
                this.etudiant = data;
            });
    }



    refresh(emp: NewUtilisateur) {
        this.etudiant = emp;
    }


    activation() {
        return this._accountService
            .activate(this.etudiantId)
            .subscribe((data) => {
                this.alert.toastSuccess("Opération réussi","Success")
                this.getEtudiant()
            });
    }

    resetPassword() {
        return this._accountService
            .resetPassword(this.etudiantId)
            .subscribe((data) => {
                this.alert.toastSuccess("Mot de passe réinitialisé","Success")
                this.getEtudiant()
            });
    }

}
