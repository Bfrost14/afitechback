import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';

@Component({
    selector: 'app-detail-professeur',
    templateUrl: './detail-professeur.component.html',
    styleUrls: ['./detail-professeur.component.scss'],
    animations: fuseAnimations,
})
export class DetailProfesseurComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() professeurId: string = "";
    professeur: NewUtilisateur;
    fiche: boolean = true;

    constructor(
        private _professeurService: AdminService,
        private _activatedRoute: ActivatedRoute
    ) {}

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

}
