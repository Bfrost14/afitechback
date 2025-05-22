import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberConverterPipe } from 'app/core/util/numberpipe';
import { ToastrModule } from 'ngx-toastr';
import { HasRoleDirective } from './hasrole.directive';
import { NgxMatDatetimePickerModule } from '@angular-material-components/datetime-picker';
import { NgxMatTimepickerModule } from '@angular-material-components/datetime-picker';


@NgModule({
    declarations: [
        NumberConverterPipe,
        HasRoleDirective
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        NgxMatDatetimePickerModule,
        NgxMatTimepickerModule
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NumberConverterPipe,
        HasRoleDirective,
        NgxMatDatetimePickerModule,
        NgxMatTimepickerModule
    ]
})
export class SharedModule {
}
