import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewCours } from '../cours.model';
import { CoursService } from '../service/cours.service';

@Component({
    selector: 'app-detail-cours',
    templateUrl: './detail-cours.component.html',
    styleUrls: ['./detail-cours.component.scss'],
    animations: fuseAnimations,
})
export class DetailCoursComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() coursId: number;
    cours: NewCours;
    fiche: boolean = true;

    constructor(
        private _coursService: CoursService,
        private _activatedRoute: ActivatedRoute
    ) {}

    ngOnInit(): void {
        
    this.getCours();

    }

    getCours() {
        return this._coursService
            .find(this.coursId)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ cours one  @@@@@@@@@@@@',
                    data
                );
                this.cours = data;
            });
    }



    refresh(emp: NewCours) {
        this.cours = emp;
    }

}
