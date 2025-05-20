import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeProfileComponent } from './liste-profile/liste-profile.component';

const routes: Routes = [
  {
      path: 'liste',
      component: ListeProfileComponent,
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
