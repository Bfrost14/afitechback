import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotation, NewNotation } from '../models/notation.model';

export type PartialUpdateNotation = Partial<INotation> & Pick<INotation, 'id'>;

export type EntityResponseType = HttpResponse<INotation>;
export type EntityArrayResponseType = HttpResponse<INotation[]>;

@Injectable({ providedIn: 'root' })
export class NotationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/notations');

  create(notation: NewNotation): Observable<any> {
    return this.http.post<INotation>(this.resourceUrl, notation, { observe: 'response' });
  }

  update(notation: INotation): Observable<any> {
    return this.http.put<INotation>(`${this.resourceUrl}/${this.getNotationIdentifier(notation)}`, notation, { observe: 'response' });
  }

  partialUpdate(notation: PartialUpdateNotation): Observable<any> {
    return this.http.patch<INotation>(`${this.resourceUrl}/${this.getNotationIdentifier(notation)}`, notation, { observe: 'response' });
  }

  find(id: number): Observable<any> {
    return this.http.get<INotation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<INotation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotationIdentifier(notation: Pick<INotation, 'id'>): number {
    return notation.id;
  }

  compareNotation(o1: Pick<INotation, 'id'> | null, o2: Pick<INotation, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotationIdentifier(o1) === this.getNotationIdentifier(o2) : o1 === o2;
  }

  addNotationToCollectionIfMissing<Type extends Pick<INotation, 'id'>>(
    notationCollection: Type[],
    ...notationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notations: Type[] = notationsToCheck.filter(isPresent);
    if (notations.length > 0) {
      const notationCollectionIdentifiers = notationCollection.map(notationItem => this.getNotationIdentifier(notationItem));
      const notationsToAdd = notations.filter(notationItem => {
        const notationIdentifier = this.getNotationIdentifier(notationItem);
        if (notationCollectionIdentifiers.includes(notationIdentifier)) {
          return false;
        }
        notationCollectionIdentifiers.push(notationIdentifier);
        return true;
      });
      return [...notationsToAdd, ...notationCollection];
    }
    return notationCollection;
  }
}
