import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeEtudiantComponent } from './liste-etudiant/liste-etudiant.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeEtudiantComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EtudiantRoutingModule {}
