import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from '../auth.service';
import { User } from 'app/core/user/user.types';
import { AdminService } from 'app/modules/admin/user/service/admin.service';
import { AlertToastService } from 'app/core/util/alertToast.service';

@Injectable({
  providedIn: 'root',
})
export class RedirectByRoleGuard implements CanActivate {
  constructor(private authService: AuthService,
    private userService: AdminService,
    private router: Router,
    private alertToast: AlertToastService) { }

  canActivate(): Observable<boolean> {
    return this.authService.getUtilisateurOb().pipe(
      map((user: any) => {
        if (user == null) {
          this.router.navigate(['sign-in']);
        } else {
          this.userService.get(user.email).subscribe(
            data => {
              console.log("logggggg data", data)
              if (data.profil == null) {
                this.alertToast.toastDanger("Vous n'avez pas de profil", "Echec de la connexion")
                this.router.navigate(['sign-out']);
              } else {
                if (data.profil.redirection == "") {
                  this.router.navigate(["/gestion/calendrier/liste"]);
                } else {
                  this.router.navigate([data.profil.redirection]);
                }

              }


            }
          )
        }

        return false;
      })
    );
  }
}
