import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { createRequestOption } from 'app/core/request/request-util';
import { ICahierTexte, NewCahierTexte } from '../cahier-texte.model';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { DATE_FORMAT } from 'app/core/config/input.constants';

export type PartialUpdateCahierTexte = Partial<ICahierTexte> & Pick<ICahierTexte, 'id'>;

type RestOf<T extends ICahierTexte | NewCahierTexte> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestCahierTexte = RestOf<ICahierTexte>;

export type NewRestCahierTexte = RestOf<NewCahierTexte>;

export type PartialUpdateRestCahierTexte = RestOf<PartialUpdateCahierTexte>;

export type EntityResponseType = HttpResponse<ICahierTexte>;
export type EntityArrayResponseType = HttpResponse<ICahierTexte[]>;

@Injectable({ providedIn: 'root' })
export class CahierTexteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/cahier-textes');

  create(cahierTexte: NewCahierTexte): Observable<any> {
    const copy = this.convertDateFromClient(cahierTexte);
    return this.http
      .post<RestCahierTexte>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cahierTexte: ICahierTexte): Observable<any> {
    const copy = this.convertDateFromClient(cahierTexte);
    return this.http
      .put<RestCahierTexte>(`${this.resourceUrl}/${this.getCahierTexteIdentifier(cahierTexte)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cahierTexte: PartialUpdateCahierTexte): Observable<any> {
    const copy = this.convertDateFromClient(cahierTexte);
    return this.http
      .patch<RestCahierTexte>(`${this.resourceUrl}/${this.getCahierTexteIdentifier(cahierTexte)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<any> {
    return this.http
      .get<RestCahierTexte>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCahierTexte[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCahierTexteIdentifier(cahierTexte: Pick<ICahierTexte, 'id'>): number {
    return cahierTexte.id;
  }

  compareCahierTexte(o1: Pick<ICahierTexte, 'id'> | null, o2: Pick<ICahierTexte, 'id'> | null): boolean {
    return o1 && o2 ? this.getCahierTexteIdentifier(o1) === this.getCahierTexteIdentifier(o2) : o1 === o2;
  }

  addCahierTexteToCollectionIfMissing<Type extends Pick<ICahierTexte, 'id'>>(
    cahierTexteCollection: Type[],
    ...cahierTextesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cahierTextes: Type[] = cahierTextesToCheck.filter(isPresent);
    if (cahierTextes.length > 0) {
      const cahierTexteCollectionIdentifiers = cahierTexteCollection.map(cahierTexteItem => this.getCahierTexteIdentifier(cahierTexteItem));
      const cahierTextesToAdd = cahierTextes.filter(cahierTexteItem => {
        const cahierTexteIdentifier = this.getCahierTexteIdentifier(cahierTexteItem);
        if (cahierTexteCollectionIdentifiers.includes(cahierTexteIdentifier)) {
          return false;
        }
        cahierTexteCollectionIdentifiers.push(cahierTexteIdentifier);
        return true;
      });
      return [...cahierTextesToAdd, ...cahierTexteCollection];
    }
    return cahierTexteCollection;
  }

  protected convertDateFromClient<T extends ICahierTexte | NewCahierTexte | PartialUpdateCahierTexte>(cahierTexte: T): RestOf<T> {
    return {
      ...cahierTexte,
      date: cahierTexte.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCahierTexte: RestCahierTexte): ICahierTexte {
    return {
      ...restCahierTexte,
      date: restCahierTexte.date ? dayjs(restCahierTexte.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCahierTexte>): HttpResponse<ICahierTexte> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCahierTexte[]>): HttpResponse<ICahierTexte[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
