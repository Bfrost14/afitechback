import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { catchError, Observable, of, switchMap, throwError } from 'rxjs';
import { AuthUtils } from 'app/core/auth/auth.utils';
import { UserService } from 'app/core/user/user.service';
import { ApplicationConfigService } from '../config/config/application-config.service';
import { User } from '../user/user.types';
type EntityArrayResponseType = HttpResponse<Map<string, object>>;
@Injectable()
export class AuthService {
    private _authenticated: boolean = false;
    public resourceUrl =
        this.applicationConfigService.getEndpointForAuth('/auth');
    public resourceUrl2 =
        this.applicationConfigService.getEndpointFor('/users');

    /**
     * Constructor
     */
    constructor(
        private _httpClient: HttpClient,
        private applicationConfigService: ApplicationConfigService,
        private _userService: UserService
    ) {}

    /**
     * Setter & getter for access token
     */
    set authenticated(auth: boolean) {
        this._authenticated = auth;
    }

    get authenticated(): boolean {
        return this._authenticated;
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Setter & getter for access token
     */
    set accessToken(token: string) {
        localStorage.setItem('accessToken', token);
    }

    get accessToken(): string {
        return localStorage.getItem('accessToken') ?? '';
    }

    /**
     * Setter & getter for access token
     */
    set refreshToken(token: string) {
        localStorage.setItem('refreshToken', token);
    }

    get refreshToken(): string {
        return localStorage.getItem('refreshToken') ?? '';
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Forgot password
     *
     * @param email
     */
    forgotPassword(email: string): Observable<any> {
        return this._httpClient.get(this.resourceUrl + '/forgot-password?email='+email);
    }

    /**
     * Reset password
     *
     * @param password
     */
    resetPassword(credentials: {email: string,password: string}): Observable<any> {
        return this._httpClient.post(this.resourceUrl + '/reset-password', credentials).pipe(
            switchMap((response: any) => {
                if (response.access_token != null) {
                    // Store the access token in the local storage
                    this.accessToken = response.access_token;
                    console.log('access_token====> ', this.accessToken);
                    // Store the refresh token in the local storage
                    this.refreshToken = response.refresh_token;
                    // Set the authenticated flag to true
                    this._authenticated = true;
                }

                // Store the user on the user service
                // this._userService.user = response.user;

                // Return a new observable with the response
                return of(response);
            })
        );
    }

    /**
     * Get users
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
    getUsers(
        page: number = 0,
        size: number = 10,
        sort: string = 'code',
        order: 'asc' | 'desc' | '' = 'desc'
    ): Observable<EntityArrayResponseType> {
        const pagination1 = {
            sort: sort + ',' + order,
            page: page,
            size: size,
        };
        return this._httpClient.get<any>(this.resourceUrl2, {
            params: pagination1,
            observe: 'response',
        });
    }

    /**
     * Sign in
     *
     * @param credentials
     */
    signIn(credentials: { email: string; password: string }): Observable<any> {
        // Throw error, if the user is already logged in
        if (this._authenticated) {
            return throwError('User is already logged in.');
        }

        return this._httpClient
            .post(this.resourceUrl + '/login', credentials)
            .pipe(
                switchMap((response: any) => {
                    if (response.access_token != null) {
                        // Store the access token in the local storage
                        this.accessToken = response.access_token;
                        console.log('access_token====> ', this.accessToken);
                        // Store the refresh token in the local storage
                        this.refreshToken = response.refresh_token;
                        // Set the authenticated flag to true
                        this._authenticated = true;
                    }

                    // Store the user on the user service
                    // this._userService.user = response.user;

                    // Return a new observable with the response
                    return of(response);
                })
            );
    }

    validateOTP(credentials: { email: string; otp: string }): Observable<any> {
        // Throw error, if the user is already logged in
        const param = {
            email: localStorage.getItem("email"),
            otp: credentials.otp,
        };

        return this._httpClient
            .get(this.resourceUrl + '/validate-otp', {
                params: param,
                observe: 'response',
            })
            .pipe(
                switchMap((response: any) => {
                    // Store the access token in the local storage
                    this.accessToken = response.body.access_token;
                    console.log('access_token====> ', response.body);
                    // Store the refresh token in the local storage
                    this.refreshToken = response.body.refresh_token;
                    // Set the authenticated flag to true
                    this._authenticated = true;

                    // Store the user on the user service
                    // this._userService.user = response.body.user;

                    // Return a new observable with the response.body
                    return of(response.body);
                })
            );
    }

    validateOTP2(credentials: { email: string; otp: string }): Observable<any> {
        // Throw error, if the user is already logged in
        const param = {
            email: credentials.email,
            otp: credentials.otp,
        };

        return this._httpClient
            .get(this.resourceUrl + '/validate-otp', {
                params: param,
                observe: 'response',
            })
            .pipe(
                switchMap((response: any) => {
                    // Store the access token in the local storage
                
                    // Store the user on the user service
                    // this._userService.user = response.body.user;

                    // Return a new observable with the response.body
                    return of(response);
                })
            );
    }

    /**
     * Sign in using the access token
     */
    signInUsingToken(): Observable<any> {
        // Sign in using the token
        return this._httpClient
            .post(this.resourceUrl + '/refresh-token', {
                refresh_token: this.refreshToken,
                accessToken: this.accessToken,
            })
            .pipe(
                catchError(() =>
                    // Return false
                    of(false)
                ),
                switchMap((response: any) => {
                    console.log(
                        '***************** refresh response *****************',
                        response
                    );
                    // Replace the access token with the new one if it's available on
                    // the response object.
                    //
                    // This is an added optional step for better security. Once you sign
                    // in using the token, you should generate a new one on the server
                    // side and attach it to the response object. Then the following
                    // piece of code can replace the token with the refreshed one.
                    if (response.access_token && response.refreshToken) {
                        console.log(
                            '***************** refresh ok *****************'
                        );
                        this.accessToken = response.access_token;
                        this.refreshToken = response.refresh_token;
                    }

                    // Set the authenticated flag to true
                    this._authenticated = true;

                    // Store the user on the user service
                    // this._userService.user = response.user;

                    // Return true
                    return of(response);
                })
            );
    }

    /**
     * Sign out
     */
    signOut(): Observable<any> {
        // Remove the access token from the local storage
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('email');

        // Set the authenticated flag to false
        this._authenticated = false;

        // Sign in using the token
        return this._httpClient
            .post(this.resourceUrl + '/logout', {
                refreshToken: this.refreshToken,
            })
            .pipe(
                catchError(() =>
                    // Return false
                    of(false)
                ),
                switchMap((response: any) => {
                    // Remove the access token from the local storage
                    localStorage.removeItem('refreshToken');

                    // Return true
                    return of(true);
                })
            );
    }

    /**
     * Sign up
     *
     * @param user
     */
    signUp(user: {
        nom: string;
        email: string;
        password: string;
        role: string;
    }): Observable<any> {
        console.log('==========> Register value ===>', user);

        return this._httpClient.post(this.resourceUrl + '/register', user);
    }

    /**
     * Unlock session
     *
     * @param credentials
     */
    unlockSession(credentials: {
        email: string;
        password: string;
    }): Observable<any> {
        return this._httpClient.post('api/auth/unlock-session', credentials);
    }

    /**
     * Check the authentication status
     */
    check(): Observable<boolean> {
        // Check if the user is logged in
        if (this._authenticated) {
            return of(true);
        }

        // Check the access token availability
        if (!this.accessToken) {
            return of(false);
        }

        // Check the access token expire date
        if (AuthUtils.isTokenExpired(this.accessToken)) {
            return of(false);
        }

        // If the access token exists and it didn't expire, sign in using it
        return this.signInUsingToken();
    }

    getUtilisateur(): any {
        console.log(AuthUtils.decodeToken(this.accessToken));
        return {
            id: null,
            name: AuthUtils.decodeToken(this.accessToken).aud,
            email: AuthUtils.decodeToken(this.accessToken).sub,
            role: AuthUtils.decodeToken(this.accessToken).iss,
            flotte: AuthUtils.decodeToken(this.accessToken).jti,
            avatar: 'assets/images/avatars/default-user.jpg',
            status: 'online',
        };
    }

    getUtilisateurOb(): Observable<User> {
        const tokenDecoded = AuthUtils.decodeToken(this.accessToken);
        console.log(tokenDecoded);
        if(tokenDecoded == null){
            return of(null)
        }
        const user: User = {
            id: null,
            name: tokenDecoded.aud,
            email: tokenDecoded.sub,
            role: tokenDecoded.iss,
            flotte: tokenDecoded.jti,
            avatar: 'assets/images/avatars/default-user.jpg',
            status: 'online',
        };
    
        return of(user);
    }

    /**
     * Update user
     *
     * @param id
     * @param user
     */
    updateuser(id: number, user: any): Observable<any> {
        return this._httpClient.put<any>(this.resourceUrl2 + '/' + id, user, {
            observe: 'response',
        });
    }
    deleteuser(id: number): Observable<HttpResponse<{}>> {
        return this._httpClient.delete(`${this.resourceUrl2}/${id}`, {
            observe: 'response',
        });
    }

    get(id: number): Observable<any> {
        return this._httpClient.get(this.resourceUrl2 + '/' + id);
    }

    getByEmail(email: string): Observable<any> {
        return this._httpClient.get(this.resourceUrl2 + '/email/' + email);
    }
}
