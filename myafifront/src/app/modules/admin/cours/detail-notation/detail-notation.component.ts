import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { ActivatedRoute } from '@angular/router';
import { NotationService } from '../service/notation.service';
import { NewNotation } from '../models/notation.model';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: 'app-detail-notation',
  templateUrl: './detail-notation.component.html',
  styleUrls: ['./detail-notation.component.css'],
  animations: fuseAnimations,
})
export class DetailNotationComponent implements OnInit {
    
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
    @Input() notationId: number;
    notation: NewNotation;
    fiche: boolean = true;

    constructor(
        private _notationService: NotationService,
        private _activatedRoute: ActivatedRoute
    ) {}

    ngOnInit(): void {
        
    this.getNotation();

    }

    getNotation() {
        return this._notationService
            .find(this.notationId)
            .subscribe((data) => {
               
                this.notation = data.body;
            });
    }



    refresh(emp: NewNotation) {
        this.notation = emp;
    }

}
