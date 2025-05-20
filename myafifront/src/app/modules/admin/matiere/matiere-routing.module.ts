import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeMatiereComponent } from './liste-matiere/liste-matiere.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListeMatiereComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MatiereRoutingModule {}
