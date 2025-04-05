import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeEtudiantComponent } from './liste-etudiant/liste-etudiant.component';
import { MesNotesComponent } from './mes-notes/mes-notes.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeEtudiantComponent,
  },
  {
    path: 'mesnotes',
    component: MesNotesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EtudiantRoutingModule {}
