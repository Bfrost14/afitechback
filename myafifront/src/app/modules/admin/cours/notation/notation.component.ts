import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import dayjs from 'dayjs/esm';

import { ICalendrierCours } from '../../calendrier-cours/calendrier-cours.model';
import { IUtilisateur }   from '../../ue/ue.model';
import { INotation, NewNotation } from '../models/notation.model';
import { NotationService } from '../service/notation.service';
import { AuthService } from 'app/core/auth/auth.service';

@Component({
  selector   : 'app-notation',
  templateUrl: './notation.component.html',
  styleUrls  : ['./notation.component.css']
})
export class NotationComponent implements OnInit {

  /** ➡️ passés par le parent (fiche cours, tableau des étudiants, …) */
  @Input() cours!: ICalendrierCours;
  @Input() etudiant!      : IUtilisateur;

  form!: FormGroup;
  notation: INotation | null = null;
  withinWindow = false;

  constructor(
    private fb: FormBuilder,
    private service: NotationService,
    private snack: MatSnackBar,
    private _authService: AuthService
  ) {}

  /* ---------------------------------------------------------- */
  ngOnInit(): void {
    /** 1. Crée le formulaire (désactivé par défaut) */
    this.form = this.fb.group({
      note        : [{ value: null, disabled: true }, [Validators.min(0), Validators.max(20)]],
      appreciation: [{ value: '',   disabled: true }]
    });

    /** 2. Vérifie la fenêtre d’édition */
    const now   = dayjs();
    const start = dayjs(this.cours.dateDebut);
    const end   = dayjs(this.cours.dateFin).add(1, 'hour');
    this.withinWindow = now.isAfter(start) && now.isBefore(end);

    /** 3. Charge la notation existante pour cet étudiant / cours */
    this.service.query({
      'idCalendrier': this.cours.id,
      'etudiant'       : this._authService.getUtilisateur().email
    }).subscribe(res => {
      const body = res.body.data ?? [];
      if (body.length) {
        this.notation = body[0];
        this.form.patchValue({
          note        : this.notation.note,
          appreciation: this.notation.appreciation
        });
      }
      /* 4. Active les champs seulement si la fenêtre est ouverte */
      if (this.withinWindow) this.form.enable();
      else this.snack.open('La période de notation est close ; lecture seule.', 'OK', { duration: 4000 });
    });
  }

  /* ---------------------------------------------------------- */
  save(): void {
    if (!this.withinWindow || this.form.invalid) return;

    const { note, appreciation } = this.form.value;

    const payload: INotation | NewNotation = this.notation
      ? { ...this.notation, note, appreciation }
      : {
          id          : null,
          note,
          appreciation,
          calendrierCours: { id: this.cours.id },
          etudiant       : { id: this._authService.getUtilisateur().id }
        };

    const op$ = this.notation ? this.service.update(payload) : this.service.create(payload);

    op$.subscribe({
      next : () => this.snack.open('Notation enregistrée', 'OK', { duration: 3000 }),
      error: ()  => this.snack.open('Erreur d’enregistrement', 'Fermer', { duration: 4000 })
    });
  }
}
