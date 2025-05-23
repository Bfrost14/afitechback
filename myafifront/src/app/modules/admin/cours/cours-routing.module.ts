import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeCoursComponent } from './liste-cours/liste-cours.component';

const routes: Routes = [
  {
    path: 'liste',
    component: ListeCoursComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoursRoutingModule {}
