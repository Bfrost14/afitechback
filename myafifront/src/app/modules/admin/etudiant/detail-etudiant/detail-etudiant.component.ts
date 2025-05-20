import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';
@Component({
    selector: 'app-detail-etudiant',
    templateUrl: './detail-etudiant.component.html',
    styleUrls: ['./detail-etudiant.component.scss'],
    animations: fuseAnimations,
})
export class DetailEtudiantComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() etudiantId: number;
    etudiant: NewUtilisateur;
    fiche: boolean = true;

    constructor(
        private _etudiantService: AdminService,
        private _activatedRoute: ActivatedRoute
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

}
