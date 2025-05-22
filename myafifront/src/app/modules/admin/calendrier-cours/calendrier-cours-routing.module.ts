import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalendrierCoursComponent } from './calendrier-cours/calendrier-cours.component';

const routes: Routes = [
  {
    path: "liste",
    component: CalendrierCoursComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CalendrierRoutingModule {}
