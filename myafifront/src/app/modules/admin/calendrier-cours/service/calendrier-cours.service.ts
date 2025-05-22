import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { createRequestOption } from 'app/core/request/request-util';
import { ICalendrierCours, NewCalendrierCours } from '../calendrier-cours.model';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';

export type PartialUpdateCalendrierCours = Partial<ICalendrierCours> & Pick<ICalendrierCours, 'id'>;

type RestOf<T extends ICalendrierCours | NewCalendrierCours> = Omit<T, 'dateDebut' | 'dateFin'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
};

export type RestCalendrierCours = RestOf<ICalendrierCours>;

export type NewRestCalendrierCours = RestOf<NewCalendrierCours>;


export type EntityResponseType = HttpResponse<ICalendrierCours>;
export type EntityArrayResponseType = HttpResponse<ICalendrierCours[]>;

@Injectable({ providedIn: 'root' })
export class CalendrierCoursService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/calendrier-cours');

  create(calendrierCours: NewCalendrierCours[]): Observable<any> {
   
    return this.http
      .post<RestCalendrierCours>(this.resourceUrl, calendrierCours, { observe: 'response' })
      ;
  }

  update(calendrierCours: ICalendrierCours): Observable<any> {
   
    return this.http
      .put<RestCalendrierCours>(`${this.resourceUrl}/${this.getCalendrierCoursIdentifier(calendrierCours)}`, calendrierCours, { observe: 'response' })
      ;
  }

  partialUpdate(calendrierCours: PartialUpdateCalendrierCours): Observable<any> {
   
    return this.http
      .patch<RestCalendrierCours>(`${this.resourceUrl}/${this.getCalendrierCoursIdentifier(calendrierCours)}`, calendrierCours, {
        observe: 'response',
      })
      ;
  }

  find(id: number): Observable<any> {
    return this.http
      .get<RestCalendrierCours>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      ;
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCalendrierCours[]>(this.resourceUrl, { params: options, observe: 'response' })
      ;
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCalendrierCoursIdentifier(calendrierCours: Pick<ICalendrierCours, 'id'>): number {
    return calendrierCours.id;
  }

  compareCalendrierCours(o1: Pick<ICalendrierCours, 'id'> | null, o2: Pick<ICalendrierCours, 'id'> | null): boolean {
    return o1 && o2 ? this.getCalendrierCoursIdentifier(o1) === this.getCalendrierCoursIdentifier(o2) : o1 === o2;
  }

  addCalendrierCoursToCollectionIfMissing<Type extends Pick<ICalendrierCours, 'id'>>(
    calendrierCoursCollection: Type[],
    ...calendrierCoursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const calendrierCours: Type[] = calendrierCoursToCheck.filter(isPresent);
    if (calendrierCours.length > 0) {
      const calendrierCoursCollectionIdentifiers = calendrierCoursCollection.map(calendrierCoursItem =>
        this.getCalendrierCoursIdentifier(calendrierCoursItem),
      );
      const calendrierCoursToAdd = calendrierCours.filter(calendrierCoursItem => {
        const calendrierCoursIdentifier = this.getCalendrierCoursIdentifier(calendrierCoursItem);
        if (calendrierCoursCollectionIdentifiers.includes(calendrierCoursIdentifier)) {
          return false;
        }
        calendrierCoursCollectionIdentifiers.push(calendrierCoursIdentifier);
        return true;
      });
      return [...calendrierCoursToAdd, ...calendrierCoursCollection];
    }
    return calendrierCoursCollection;
  }


}
