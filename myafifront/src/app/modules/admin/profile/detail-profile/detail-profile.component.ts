import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { NewProfile } from '../profile.model';
import { ProfileService } from '../service/profile.service';


@Component({
    selector: 'app-detail-profile',
    templateUrl: './detail-profile.component.html',
    styleUrls: ['./detail-profile.component.scss'],
    animations: fuseAnimations,
})
export class DetailProfileComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() profileId: number;
    profile: NewProfile;
    fiche: boolean = true;

    constructor(
        private _profileService: ProfileService,
        private _activatedRoute: ActivatedRoute
    ) {}

    ngOnInit(): void {
        
    this.getProfile();

    }

    getProfile() {
        return this._profileService
            .find(this.profileId)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ profile one  @@@@@@@@@@@@',
                    data
                );
                this.profile = data.body;
            });
    }



    refresh(emp: NewProfile) {
        this.profile = emp;
    }

}
