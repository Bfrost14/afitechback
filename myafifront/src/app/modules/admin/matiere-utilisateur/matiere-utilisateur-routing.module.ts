import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeMatiereUtilisateurComponent } from './liste-matiere-utilisateur/liste-matiere-utilisateur.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListeMatiereUtilisateurComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MatiereUtilisateurRoutingModule {}
