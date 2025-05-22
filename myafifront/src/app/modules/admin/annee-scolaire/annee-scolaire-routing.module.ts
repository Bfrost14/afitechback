import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeAnneeScolaireComponent } from './liste-annee-scolaire/liste-annee-scolaire.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListeAnneeScolaireComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AnneeScolaireRoutingModule {}
