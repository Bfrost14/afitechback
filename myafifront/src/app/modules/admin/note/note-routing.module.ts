import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeNoteComponent } from './liste-note/liste-note.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeNoteComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NoteRoutingModule {}
