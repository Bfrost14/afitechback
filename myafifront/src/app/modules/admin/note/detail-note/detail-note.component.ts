import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-detail-note',
  templateUrl: './detail-note.component.html',
  styleUrls: ['./detail-note.component.css']
})
export class DetailNoteComponent implements OnInit {
  @Input() matiereUtilisateurId: any;
  @Input() matiereUser: any
  constructor() { }

  ngOnInit() {
  }

}
