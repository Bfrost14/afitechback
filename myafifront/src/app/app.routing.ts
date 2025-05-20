import { Route } from '@angular/router';
import { AuthGuard } from 'app/core/auth/guards/auth.guard';
import { NoAuthGuard } from 'app/core/auth/guards/noAuth.guard';
import { LayoutComponent } from 'app/layout/layout.component';
import { InitialDataResolver } from 'app/app.resolvers';
import { RedirectByRoleGuard } from './core/auth/guards/redirect-by-role.guard';
import { EmptyComponent } from './modules/admin/empty/empty.component';

// @formatter:off
/* eslint-disable max-len */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
export const appRoutes: Route[] = [
 
    {
        path: '',
        pathMatch: 'full',
        canActivate: [RedirectByRoleGuard],
        component: EmptyComponent
      },
      {
        path: 'signed-in-redirect',
        pathMatch: 'full',
        canActivate: [RedirectByRoleGuard],
        component: EmptyComponent
      },
      

    // Auth routes for guests
    {
        path: '',
        canActivate: [NoAuthGuard],
        canActivateChild: [NoAuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            {path: 'reset-password', loadChildren: () => import('app/modules/auth/reset-password/reset-password.module').then(m => m.AuthResetPasswordModule)},
            {path: 'sign-in', loadChildren: () => import('app/modules/auth/sign-in/sign-in.module').then(m => m.AuthSignInModule)}
        ]
    },

    // Auth routes for authenticated users
    {
        path: '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            {path: 'sign-out', loadChildren: () => import('app/modules/auth/sign-out/sign-out.module').then(m => m.AuthSignOutModule)}
        ]
    },

    // Landing routes
    {
        path: '',
        component  : LayoutComponent,
        data: {
            layout: 'empty'
        },
        children   : [
            {path: 'home', loadChildren: () => import('app/modules/landing/home/home.module').then(m => m.LandingHomeModule)},
        ]
    },

    // Admin routes
    {
        path       : '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component  : LayoutComponent,
        resolve    : {
            initialData: InitialDataResolver,
        },
        children   : [

            // Dashboards
            {path: 'admin', children: [
                {path: 'profiles', loadChildren: () => import('app/modules/admin/profile/profile.module').then(m => m.ProfileModule)},             
                {path: 'ue', loadChildren: () => import('app/modules/admin/ue/ue.module').then(m => m.UeModule)},             
                {path: 'semestre', loadChildren: () => import('app/modules/admin/semestre/semestre.module').then(m => m.SemestreModule)},             
                {path: 'campus', loadChildren: () => import('app/modules/admin/campus/campus.module').then(m => m.CampusModule)},             
                {path: 'salle', loadChildren: () => import('app/modules/admin/salle/salle.module').then(m => m.SalleModule)},  
                {path: 'matiere', loadChildren: () => import('app/modules/admin/matiere/matiere.module').then(m => m.MatiereModule)},  
                {path: 'filiere', loadChildren: () => import('app/modules/admin/filiere/filiere.module').then(m => m.FiliereModule)},  
                {path: 'professeur', loadChildren: () => import('app/modules/admin/professeur/professeur.module').then(m => m.ProfesseurModule)},  
                //{path: 'cours', loadChildren: () => import('app/modules/admin/cours/cours.module').then(m => m.CoursModule)},  

            ]},
            
            
            // 404 & Catch all
            {path: '**', redirectTo: '404-not-found'}
        ]
    }
];
