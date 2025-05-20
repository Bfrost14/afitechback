import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { createRequestOption } from 'app/core/request/request-util';
import { ICampus, NewCampus } from '../campus.model';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';

export type PartialUpdateCampus = Partial<ICampus> & Pick<ICampus, 'id'>;

export type EntityResponseType = HttpResponse<ICampus>;
export type EntityArrayResponseType = HttpResponse<ICampus[]>;

@Injectable({ providedIn: 'root' })
export class CampusService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/campuses');

  create(campus: NewCampus): Observable<any> {
    return this.http.post<ICampus>(this.resourceUrl, campus, { observe: 'response' });
  }

  update(campus: ICampus): Observable<any> {
    return this.http.put<ICampus>(`${this.resourceUrl}/${this.getCampusIdentifier(campus)}`, campus, { observe: 'response' });
  }

  partialUpdate(campus: PartialUpdateCampus): Observable<any> {
    return this.http.patch<ICampus>(`${this.resourceUrl}/${this.getCampusIdentifier(campus)}`, campus, { observe: 'response' });
  }

  find(id: number): Observable<any> {
    return this.http.get<ICampus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<ICampus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCampusIdentifier(campus: Pick<ICampus, 'id'>): number {
    return campus.id;
  }

  compareCampus(o1: Pick<ICampus, 'id'> | null, o2: Pick<ICampus, 'id'> | null): boolean {
    return o1 && o2 ? this.getCampusIdentifier(o1) === this.getCampusIdentifier(o2) : o1 === o2;
  }

  addCampusToCollectionIfMissing<Type extends Pick<ICampus, 'id'>>(
    campusCollection: Type[],
    ...campusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const campuses: Type[] = campusesToCheck.filter(isPresent);
    if (campuses.length > 0) {
      const campusCollectionIdentifiers = campusCollection.map(campusItem => this.getCampusIdentifier(campusItem));
      const campusesToAdd = campuses.filter(campusItem => {
        const campusIdentifier = this.getCampusIdentifier(campusItem);
        if (campusCollectionIdentifiers.includes(campusIdentifier)) {
          return false;
        }
        campusCollectionIdentifiers.push(campusIdentifier);
        return true;
      });
      return [...campusesToAdd, ...campusCollection];
    }
    return campusCollection;
  }
}
