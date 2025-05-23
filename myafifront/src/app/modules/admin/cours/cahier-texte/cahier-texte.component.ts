import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ICahierTexte, NewCahierTexte } from '../models/cahier-texte.model';
import dayjs from 'dayjs/esm';
import { ICalendrierCours } from '../../calendrier-cours/calendrier-cours.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CahierTexteService } from '../service/cahier-texte.service';
import { AuthService } from 'app/core/auth/auth.service';

@Component({
  selector: 'app-cahier-texte',
  templateUrl: './cahier-texte.component.html',
  styleUrls: ['./cahier-texte.component.css']
})
export class CahierTexteComponent implements OnInit {
  @Input() cours: any;

  form: FormGroup;
  cahierTexte: ICahierTexte | null = null;
  isWithinEditWindow: boolean = false;

  constructor(
    private fb: FormBuilder,
    private service: CahierTexteService,
    private snackBar: MatSnackBar,
    private _authService: AuthService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      contenu: [{ value: '', disabled: true }]
    });

    console.log(this._authService.getUtilisateur())

    this.loadCahierTexte();
    this.checkEditWindow();
  }

  loadCahierTexte(): void {
    this.service.query({ 'idCalendrier': this.cours.id }).subscribe(res => {
      console.log(res)
      const body = res.body.data || [];
      if (body.length > 0) {
        this.cahierTexte = body[0];
        console.log(this.cahierTexte)
        this.form.patchValue({ contenu: this.cahierTexte.contenu });
        if (this.isWithinEditWindow) {
          this.form.get('contenu').enable();
        }
      } else if (this.isWithinEditWindow) {
        this.form.get('contenu').enable();
      }
    });
  }

  checkEditWindow(): void {
    const now = dayjs();
    const start = dayjs(this.cours.dateDebut);
    const end = dayjs(this.cours.dateFin).add(1, 'hour');

    this.isWithinEditWindow = now.isAfter(start) && now.isBefore(end);
    if (!this.isWithinEditWindow) {
      this.snackBar.open(
        'La période de saisie du cahier de texte est terminée.',
        'Fermer',
        { duration: 4000 }
      );
    }
  }

  save(): void {
    if (!this.isWithinEditWindow || this.form.invalid) return;

    const contenu = this.form.get('contenu').value;
    const payload: ICahierTexte | NewCahierTexte = this.cahierTexte
      ? { ...this.cahierTexte, contenu }
      : {
          id: null,
          contenu,
          date: dayjs(),
          calendrierCours:  this.cours,
          user: { id: this._authService.getUtilisateur().id } // ou à adapter
        };

    const operation = this.cahierTexte
      ? this.service.update(payload)
      : this.service.create(payload);

    operation.subscribe(() => {
      this.snackBar.open('Cahier de texte enregistré avec succès', 'Fermer', {
        duration: 3000
      });
    });
  }
}
