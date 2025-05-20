import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeSemestreComponent } from './liste-semestre/liste-semestre.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeSemestreComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SemestreRoutingModule {}
