import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from '../auth.service';
import { User } from 'app/core/user/user.types';

@Injectable({
  providedIn: 'root',
})
export class RedirectByRoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.authService.getUtilisateurOb().pipe(
        map((user: User) => {
            if(user == null){
                this.router.navigate(['sign-in']);
            }else{
                if (user.role === 'ROLE_SECRETAIRE') {
                    this.router.navigate(['dashboards/etudiants/liste']);
                  } else {
                    this.router.navigate(['dashboards/etudiants/mesnotes']);
                  }
            }
          
          return false;
        })
      );
  }
}
