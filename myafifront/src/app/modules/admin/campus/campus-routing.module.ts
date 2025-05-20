import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeCampusComponent } from './liste-campus/liste-campus.component';

const routes: Routes = [
  {
    path: "liste",
    component: ListeCampusComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CampusRoutingModule {}
