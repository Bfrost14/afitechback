import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeSalleComponent } from './liste-salle/liste-salle.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeSalleComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SalleRoutingModule {}
