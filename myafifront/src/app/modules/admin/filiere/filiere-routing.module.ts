import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeFiliereComponent } from './liste-filiere/liste-filiere.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListeFiliereComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FiliereRoutingModule {}
