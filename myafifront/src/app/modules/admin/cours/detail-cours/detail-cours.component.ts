import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FuseAlertType } from '@fuse/components/alert';
import { CalendrierCoursService } from '../../calendrier-cours/service/calendrier-cours.service';
import { NewCalendrierCours } from '../../calendrier-cours/calendrier-cours.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detail-cours',
  templateUrl: './detail-cours.component.html',
  styleUrls: ['./detail-cours.component.css']
})
export class DetailCoursComponent implements OnInit {

  alert: { type: FuseAlertType; message: string } = {
    type: 'success',
    message: '',
  };
  showAlert: boolean = false;
  @Output() actualiser: EventEmitter<number> = new EventEmitter<number>();
  @Input() coursId: number;
  cours: NewCalendrierCours;
  fiche: boolean = true;

  constructor(
    private _coursService: CalendrierCoursService
  ) { }

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
        this.cours = data.body;
      });
  }



  refresh(emp: NewCalendrierCours) {
    this.cours = emp;
  }


}
