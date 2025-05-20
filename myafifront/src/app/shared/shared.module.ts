import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberConverterPipe } from 'app/core/util/numberpipe';
import { ToastrModule } from 'ngx-toastr';
import { HasRoleDirective } from './hasrole.directive';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';

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
        NgxMatSelectSearchModule
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NumberConverterPipe,
        HasRoleDirective,
        NgxMatSelectSearchModule
    ]
})
export class SharedModule
{
}
