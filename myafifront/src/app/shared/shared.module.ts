import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NumberConverterPipe } from 'app/core/util/numberpipe';
import { ToastrModule } from 'ngx-toastr';
import { HasRoleDirective } from './hasrole.directive';

@NgModule({
    declarations: [	
    NumberConverterPipe,
      HasRoleDirective
   ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule.forRoot()
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NumberConverterPipe,
        HasRoleDirective
    ]
})
export class SharedModule
{
}
