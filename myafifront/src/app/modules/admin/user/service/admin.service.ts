import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';



@Injectable({
    providedIn: 'root'
})
export class AdminService
{
    private user: BehaviorSubject<any | null> = new BehaviorSubject(null);
    private users: BehaviorSubject<any[] | null> = new BehaviorSubject(null);
   // private resourceUrl = environment.SERVER_API_URL_COTISATION+'/v1/api/type-controleurs';
    private resourceUrl = this.applicationConfigService.getEndpointFor('/admin');
    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient, private applicationConfigService: ApplicationConfigService)
    {
    }
    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------
    

    /**
     * Getter for user
     */
    get user$(): Observable<any>
    {
        return this.user.asObservable();
    }

    /**
     * Getter for users
     */
    get users$(): Observable<any[]>
    {
        return this.users.asObservable();
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
    getuser(page: number = 0, size: number = 10, sort: string = 'id', order: 'asc' | 'desc' | '' = 'desc', matricule: string = '',  email: string = '', nom: string = '', prenom: string = '', filiere: string = ""):
        Observable<any>
    {
        const pagination1 = {
                'page'      : page,
                'size'  : size,
                'sort': sort+","+order,
                "role":"ROLE_ETUDIANT"
            };

            if(email != ""){
                pagination1["email"] = email
            }
            if(nom != ""){
                pagination1["nom"] = nom
            }

            if(prenom != null){
                pagination1["prenom"] = prenom
            }

            if(filiere != null){
                pagination1["filiere"] = filiere
            }

            if(matricule != null){
                pagination1["matricule"] = matricule
            }
         return this._httpClient.get<any>(this.resourceUrl,{params:pagination1,observe:'response'});
    }

    
    get(id: number): Observable<any>{
        return this._httpClient.get(this.resourceUrl + '/' + id)
    }

    /**
     * Create user
     */
    createuser(): Observable<any>
    {
        const newuser = {} as Observable<any>; // 👈️ type assertion
      return newuser;
    }

    /**
     * Update user
     *
     * @param id
     * @param user
     */
    updateuser(id: number,user: any): Observable<any> {
        return this._httpClient
            .put<any>(this.resourceUrl+'/'+id, user, { observe: 'response' });
    }

    saveuser(user: any): Observable<any> {
        return this._httpClient
            .post<any>(this.resourceUrl, user, { observe: 'response' });
    }
    deleteuser(id: number): Observable<HttpResponse<{}>> {
        return this._httpClient.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }



}
