import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUE, NewUE } from '../ue.model';

export type PartialUpdateUE = Partial<IUE> & Pick<IUE, 'id'>;

export type EntityResponseType = HttpResponse<IUE>;
export type EntityArrayResponseType = HttpResponse<IUE[]>;

@Injectable({ providedIn: 'root' })
export class UEService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/ues');

  create(uE: NewUE): Observable<any> {
    return this.http.post<IUE>(this.resourceUrl, uE, { observe: 'response' });
  }

  update(uE: IUE): Observable<any> {
    return this.http.put<IUE>(`${this.resourceUrl}/${this.getUEIdentifier(uE)}`, uE, { observe: 'response' });
  }

  partialUpdate(uE: PartialUpdateUE): Observable<any> {
    return this.http.patch<IUE>(`${this.resourceUrl}/${this.getUEIdentifier(uE)}`, uE, { observe: 'response' });
  }

  find(id: number): Observable<any> {
    return this.http.get<IUE>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<IUE[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUEIdentifier(uE: Pick<IUE, 'id'>): number {
    return uE.id;
  }

  compareUE(o1: Pick<IUE, 'id'> | null, o2: Pick<IUE, 'id'> | null): boolean {
    return o1 && o2 ? this.getUEIdentifier(o1) === this.getUEIdentifier(o2) : o1 === o2;
  }

  addUEToCollectionIfMissing<Type extends Pick<IUE, 'id'>>(uECollection: Type[], ...uESToCheck: (Type | null | undefined)[]): Type[] {
    const uES: Type[] = uESToCheck.filter(isPresent);
    if (uES.length > 0) {
      const uECollectionIdentifiers = uECollection.map(uEItem => this.getUEIdentifier(uEItem));
      const uESToAdd = uES.filter(uEItem => {
        const uEIdentifier = this.getUEIdentifier(uEItem);
        if (uECollectionIdentifiers.includes(uEIdentifier)) {
          return false;
        }
        uECollectionIdentifiers.push(uEIdentifier);
        return true;
      });
      return [...uESToAdd, ...uECollection];
    }
    return uECollection;
  }
}
