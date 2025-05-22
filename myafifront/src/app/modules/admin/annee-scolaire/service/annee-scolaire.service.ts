import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnneeScolaire, NewAnneeScolaire } from '../annee-scolaire.model';

export type PartialUpdateAnneeScolaire = Partial<IAnneeScolaire> & Pick<IAnneeScolaire, 'id'>;

export type EntityResponseType = HttpResponse<IAnneeScolaire>;
export type EntityArrayResponseType = HttpResponse<IAnneeScolaire[]>;

@Injectable({ providedIn: 'root' })
export class AnneeScolaireService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/annee-scolaire');

  create(filiere: NewAnneeScolaire): Observable<any> {
    return this.http.post<IAnneeScolaire>(this.resourceUrl, filiere, { observe: 'response' });
  }

  update(filiere: IAnneeScolaire): Observable<any> {
    return this.http.put<IAnneeScolaire>(`${this.resourceUrl}/${this.getAnneeScolaireIdentifier(filiere)}`, filiere, { observe: 'response' });
  }

  partialUpdate(filiere: PartialUpdateAnneeScolaire): Observable<any> {
    return this.http.patch<IAnneeScolaire>(`${this.resourceUrl}/${this.getAnneeScolaireIdentifier(filiere)}`, filiere, { observe: 'response' });
  }

  find(id: number): Observable<any> {
    return this.http.get<IAnneeScolaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<IAnneeScolaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnneeScolaireIdentifier(filiere: Pick<IAnneeScolaire, 'id'>): number {
    return filiere.id;
  }

  compareAnneeScolaire(o1: Pick<IAnneeScolaire, 'id'> | null, o2: Pick<IAnneeScolaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnneeScolaireIdentifier(o1) === this.getAnneeScolaireIdentifier(o2) : o1 === o2;
  }

  addAnneeScolaireToCollectionIfMissing<Type extends Pick<IAnneeScolaire, 'id'>>(
    filiereCollection: Type[],
    ...filieresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const filieres: Type[] = filieresToCheck.filter(isPresent);
    if (filieres.length > 0) {
      const filiereCollectionIdentifiers = filiereCollection.map(filiereItem => this.getAnneeScolaireIdentifier(filiereItem));
      const filieresToAdd = filieres.filter(filiereItem => {
        const filiereIdentifier = this.getAnneeScolaireIdentifier(filiereItem);
        if (filiereCollectionIdentifiers.includes(filiereIdentifier)) {
          return false;
        }
        filiereCollectionIdentifiers.push(filiereIdentifier);
        return true;
      });
      return [...filieresToAdd, ...filiereCollection];
    }
    return filiereCollection;
  }
}
