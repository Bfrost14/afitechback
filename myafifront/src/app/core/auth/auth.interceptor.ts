import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { AuthService } from 'app/core/auth/auth.service';
import { AuthUtils } from 'app/core/auth/auth.utils';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    urlsToNotUse: Array<string>;
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private _authService: AuthService, private _router: Router) {
        this.urlsToNotUse = [
            '/auth/' // ajoutez ici les chemins à exclure
        ];
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Vérifiez si l'URL correspond à celles à ne pas utiliser pour l'ajout du jeton
        if (this.urlsToNotUse.some(url => req.url.includes(url))) {
            return next.handle(req);
        }

        let authReq = req;
        const token = this._authService.accessToken;

        if (this._authService.accessToken && !AuthUtils.isTokenExpired(this._authService.accessToken, 60)) {
            authReq = this.addTokenHeader(req, token);
        }

        return next.handle(authReq).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    // Unauthorized - navigate to sign-out page
                    this._router.navigate(['sign-out']);
                } else if (error.status === 403) {
                    // Forbidden - navigate to unauthorized page
                    this._router.navigate(['unauthorized']);
                }
                // Pass the error to the caller
                return throwError(error);
            })
        );
    }

    private addTokenHeader(request: HttpRequest<any>, token: string) {
        return request.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`,
                username: `${localStorage.getItem('email')}`
            }
        });
    }
}