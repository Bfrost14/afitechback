import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberConverterPipe } from 'app/core/util/numberpipe';
import { ToastrModule } from 'ngx-toastr';
import { HasRoleDirective } from './hasrole.directive';
import { NgxMatDatetimePickerModule } from '@angular-material-components/datetime-picker';
import { NgxMatTimepickerModule } from '@angular-material-components/datetime-picker';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { MatIconModule } from '@angular/material/icon';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';


@NgModule({
    declarations: [
        NumberConverterPipe,
        HasRoleDirective,
        UnauthorizedComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        NgxMatDatetimePickerModule,
        NgxMatTimepickerModule,
        MatIconModule,
        NgxMatSelectSearchModule
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NumberConverterPipe,
        HasRoleDirective,
        NgxMatDatetimePickerModule,
        NgxMatTimepickerModule,
        NgxMatSelectSearchModule
    ]
})
export class SharedModule {
}
