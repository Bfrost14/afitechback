import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListePointageProfesseurComponent } from './liste-pointage-professeur/liste-pointage-professeur.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListePointageProfesseurComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PointageProfesseurRoutingModule {}
