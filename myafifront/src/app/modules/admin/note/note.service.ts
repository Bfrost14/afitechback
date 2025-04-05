import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

type EntityResponseType = HttpResponse<any>; 
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';



@Injectable({
    providedIn: 'root'
})
export class NoteService
{
    private note: BehaviorSubject<any | null> = new BehaviorSubject(null);
    private notes: BehaviorSubject<any[] | null> = new BehaviorSubject(null);
   // private resourceUrl = environment.SERVER_API_URL_COTISATION+'/v1/api/type-controleurs';
    private resourceUrl = this.applicationConfigService.getEndpointFor('/notes');
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
     * Getter for note
     */
    get note$(): Observable<any>
    {
        return this.note.asObservable();
    }

    /**
     * Getter for notes
     */
    get notes$(): Observable<any[]>
    {
        return this.notes.asObservable();
    }
    /**
     * Get notes
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
    getnote(page: number = 0, size: number = 10, sort: string = 'id', order: 'asc' | 'desc' | '' = 'desc', matiere: string = '',  semestre: string = '', nom: string = '', prenom: string = '',filiere: string = ''):
        Observable<any>
    {
        const pagination1 = {
                'page'      : page,
                'size'  : size,
                'sort': sort+","+order
            };

            if(matiere != ""){
                pagination1["matiere"] = matiere
            }
            if(nom != ""){
                pagination1["nom"] = nom
            }

            if(prenom != null){
                pagination1["prenom"] = prenom
            }

            if(semestre != null){
                pagination1["semestre"] = semestre
            }
            if(filiere != null){
                pagination1["filiere"] = filiere
            }

            
         return this._httpClient.get<any>(this.resourceUrl+"/private/all",{params:pagination1,observe:'response'});
    }

     /**
     * Get notes
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
     getnoteEtudiants(page: number = 0, size: number = 10, sort: string = 'id', order: 'asc' | 'desc' | '' = 'desc', email: string,matiere: string = '',  semestre: string = ''):
     Observable<any>
 {
     const pagination1 = {
             'page'      : page,
             'size'  : size,
             'sort': sort+","+order,
             'email': email
         };

         if(matiere != ""){
             pagination1["matiere"] = matiere
         }
    

         if(semestre != null){
             pagination1["semestre"] = semestre
         }

         
      return this._httpClient.get<any>(this.resourceUrl+"/etudiant/all",{params:pagination1,observe:'response'});
 }

    
    get(id: number): Observable<any>{
        return this._httpClient.get(this.resourceUrl + '/private/' + id)
    }

    /**
     * Create note
     */
    createnote(): Observable<any>
    {
        const newnote = {} as Observable<any>; // 👈️ type assertion
      return newnote;
    }

    /**
     * Update note
     *
     * @param id
     * @param note
     */
    updatenote(note: any): Observable<EntityResponseType> {
        return this._httpClient
            .put<any>(this.resourceUrl+'/private/update', note, { observe: 'response' });
    }

    savenote(note: any): Observable<EntityResponseType> {
        return this._httpClient
            .post<any>(this.resourceUrl+'/private', note, { observe: 'response' });
    }

    saveallnote(note: any[]): Observable<EntityResponseType> {
        return this._httpClient
            .post<any>(this.resourceUrl+'/private/all', note, { observe: 'response' });
    }

    deletenote(id: number): Observable<HttpResponse<{}>> {
        return this._httpClient.delete(`${this.resourceUrl}/private/${id}`, { observe: 'response' });
    }



}
