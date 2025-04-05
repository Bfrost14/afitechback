import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EtudiantRoutingModule } from './etudiant-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRippleModule } from '@angular/material/core';
import { MatSortModule } from '@angular/material/sort';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SharedModule } from 'app/shared/shared.module';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { DetailEtudiantComponent } from './detail-etudiant/detail-etudiant.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { FuseAlertModule } from '@fuse/components/alert';
import { ListeEtudiantComponent } from './liste-etudiant/liste-etudiant.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTabsModule } from '@angular/material/tabs';
import { QuillModule } from 'ngx-quill';
import { MesNotesComponent } from './mes-notes/mes-notes.component';
import { AjoutEtudiantComponent } from './ajout-etudiant/ajout-etudiant.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { NoteModule } from '../note/note.module';

@NgModule({
  declarations: [
    DetailEtudiantComponent,
    ListeEtudiantComponent,
    MesNotesComponent,
    AjoutEtudiantComponent
  ],
  imports: [
    CommonModule,
    EtudiantRoutingModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatMenuModule,
    MatProgressBarModule,
    MatRippleModule,
    MatSortModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatTooltipModule,
    SharedModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatDividerModule,
    MatCardModule,
    FuseAlertModule,
    MatAutocompleteModule,
    MatTabsModule,
    QuillModule.forRoot(),
    FuseAlertModule,
    SweetAlert2Module.forRoot(),
    NoteModule
  ],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtudiantModule {}
