import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeUeComponent } from './liste-ue/liste-ue.component';

const routes: Routes = [
  {
      path: 'liste',
      component: ListeUeComponent,
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UeRoutingModule {}
