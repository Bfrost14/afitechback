import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';

@Component({
    selector: 'app-detail-administration',
    templateUrl: './detail-administration.component.html',
    styleUrls: ['./detail-administration.component.scss'],
    animations: fuseAnimations,
})
export class DetailAdministrationComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() administrationId: number;
    administration: NewUtilisateur;
    fiche: boolean = true;

    constructor(
        private _administrationService: AdminService,
        private _activatedRoute: ActivatedRoute
    ) {}

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

}
