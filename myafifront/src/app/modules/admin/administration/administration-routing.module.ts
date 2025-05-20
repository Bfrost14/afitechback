import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeAdministrationComponent } from './liste-administration/liste-administration.component';


const routes: Routes = [
  {
    path: 'liste',
    component: ListeAdministrationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministrationRoutingModule {}
