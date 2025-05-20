import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeProfesseurComponent } from './liste-professeur/liste-professeur.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeProfesseurComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfesseurRoutingModule {}
