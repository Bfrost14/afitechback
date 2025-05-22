import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
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
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { FuseAlertModule } from '@fuse/components/alert';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTabsModule } from '@angular/material/tabs';
import { QuillModule } from 'ngx-quill';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendrierRoutingModule } from './calendrier-cours-routing.module';
import { CalendrierCoursComponent } from './calendrier-cours/calendrier-cours.component';
import { CalendrierCoursModalComponent } from './calendrier-cours-modal/calendrier-cours-modal.component';
import { CalendarDateFormatter, CalendarModule, DateAdapter } from 'angular-calendar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CalendrierCoursMultiDialogComponent } from './calendrier-cours-multi-dialog/calendrier-cours-multi-dialog.component';
import { CustomDateFormatter } from 'app/shared/custom-date-formatter';

@NgModule({
  declarations: [
    CalendrierCoursComponent,
    CalendrierCoursModalComponent,
    CalendrierCoursMultiDialogComponent
  ],
  imports: [
    CommonModule,
    CalendrierRoutingModule,
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
    MatDividerModule,
    MatCardModule,
    FuseAlertModule,
    MatAutocompleteModule,
    MatTabsModule,
    QuillModule.forRoot(),
    FuseAlertModule,
    SweetAlert2Module.forRoot(),
     CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    // Angular Material Modules
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatDialogModule,
  ],
schemas: [CUSTOM_ELEMENTS_SCHEMA],
providers: [
    DatePipe,
    { provide: CalendarDateFormatter, useClass: CustomDateFormatter }
  ]
})
export class CalendrierCoursModule {}
