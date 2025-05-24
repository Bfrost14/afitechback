import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';



@Injectable({
    providedIn: 'root'
})
export class AdminService {
    private user: BehaviorSubject<any | null> = new BehaviorSubject(null);
    private users: BehaviorSubject<any[] | null> = new BehaviorSubject(null);
    // private resourceUrl = environment.SERVER_API_URL_COTISATION+'/v1/api/type-controleurs';
    private resourceUrl = this.applicationConfigService.getEndpointFor('/api/admin/users');
    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient, private applicationConfigService: ApplicationConfigService) {
    }
    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------


    /**
     * Getter for user
     */
    get user$(): Observable<any> {
        return this.user.asObservable();
    }

    /**
     * Getter for users
     */
    get users$(): Observable<any[]> {
        return this.users.asObservable();
    }
    query(req?: any): Observable<any> {
        const options = createRequestOption(req);
        return this._httpClient.get<any>(this.resourceUrl, { params: options, observe: 'response' });
    }


    get(email: string): Observable<any> {
        return this._httpClient.get(this.resourceUrl + '/' + email)
    }

    /**
     * Create user
     */
    createuser(): Observable<any> {
        const newuser = {} as Observable<any>; // 👈️ type assertion
        return newuser;
    }

    /**
     * Update user
     *
     * @param id
     * @param user
     */
    updateuser(id: string, user: any): Observable<any> {
        return this._httpClient
            .put<any>(this.resourceUrl , user, { observe: 'response' });
    }

    saveuser(user: any): Observable<any> {
        return this._httpClient
            .post<any>(this.resourceUrl, user, { observe: 'response' });
    }
    deleteuser(id: number): Observable<HttpResponse<{}>> {
        return this._httpClient.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }



}
