import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointageProfesseur, NewPointageProfesseur } from '../pointage-professeur.model';

export type PartialUpdatePointageProfesseur = Partial<IPointageProfesseur> & Pick<IPointageProfesseur, 'id'>;

type RestOf<T extends IPointageProfesseur | NewPointageProfesseur> = Omit<T, 'heureArrivee' | 'heureDepart'> & {
  heureArrivee?: string | null;
  heureDepart?: string | null;
};

export type RestPointageProfesseur = RestOf<IPointageProfesseur>;

export type NewRestPointageProfesseur = RestOf<NewPointageProfesseur>;

export type PartialUpdateRestPointageProfesseur = RestOf<PartialUpdatePointageProfesseur>;

export type EntityResponseType = HttpResponse<IPointageProfesseur>;
export type EntityArrayResponseType = HttpResponse<IPointageProfesseur[]>;

@Injectable({ providedIn: 'root' })
export class PointageProfesseurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/pointage-professeurs');

  create(pointageProfesseur: NewPointageProfesseur): Observable<any> {
    const copy = this.convertDateFromClient(pointageProfesseur);
    return this.http
      .post<RestPointageProfesseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pointageProfesseur: IPointageProfesseur): Observable<any> {
    const copy = this.convertDateFromClient(pointageProfesseur);
    return this.http
      .put<RestPointageProfesseur>(`${this.resourceUrl}/${this.getPointageProfesseurIdentifier(pointageProfesseur)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pointageProfesseur: PartialUpdatePointageProfesseur): Observable<any> {
    const copy = this.convertDateFromClient(pointageProfesseur);
    return this.http
      .patch<RestPointageProfesseur>(`${this.resourceUrl}/${this.getPointageProfesseurIdentifier(pointageProfesseur)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<any> {
    return this.http
      .get<RestPointageProfesseur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPointageProfesseur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPointageProfesseurIdentifier(pointageProfesseur: Pick<IPointageProfesseur, 'id'>): number {
    return pointageProfesseur.id;
  }

  comparePointageProfesseur(o1: Pick<IPointageProfesseur, 'id'> | null, o2: Pick<IPointageProfesseur, 'id'> | null): boolean {
    return o1 && o2 ? this.getPointageProfesseurIdentifier(o1) === this.getPointageProfesseurIdentifier(o2) : o1 === o2;
  }

  addPointageProfesseurToCollectionIfMissing<Type extends Pick<IPointageProfesseur, 'id'>>(
    pointageProfesseurCollection: Type[],
    ...pointageProfesseursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pointageProfesseurs: Type[] = pointageProfesseursToCheck.filter(isPresent);
    if (pointageProfesseurs.length > 0) {
      const pointageProfesseurCollectionIdentifiers = pointageProfesseurCollection.map(pointageProfesseurItem =>
        this.getPointageProfesseurIdentifier(pointageProfesseurItem),
      );
      const pointageProfesseursToAdd = pointageProfesseurs.filter(pointageProfesseurItem => {
        const pointageProfesseurIdentifier = this.getPointageProfesseurIdentifier(pointageProfesseurItem);
        if (pointageProfesseurCollectionIdentifiers.includes(pointageProfesseurIdentifier)) {
          return false;
        }
        pointageProfesseurCollectionIdentifiers.push(pointageProfesseurIdentifier);
        return true;
      });
      return [...pointageProfesseursToAdd, ...pointageProfesseurCollection];
    }
    return pointageProfesseurCollection;
  }

  protected convertDateFromClient<T extends IPointageProfesseur | NewPointageProfesseur | PartialUpdatePointageProfesseur>(
    pointageProfesseur: T,
  ): RestOf<T> {
    return {
      ...pointageProfesseur,
      heureArrivee: pointageProfesseur.heureArrivee?.toJSON() ?? null,
      heureDepart: pointageProfesseur.heureDepart?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPointageProfesseur: RestPointageProfesseur): IPointageProfesseur {
    return {
      ...restPointageProfesseur,
      heureArrivee: restPointageProfesseur.heureArrivee ? dayjs(restPointageProfesseur.heureArrivee) : undefined,
      heureDepart: restPointageProfesseur.heureDepart ? dayjs(restPointageProfesseur.heureDepart) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPointageProfesseur>): HttpResponse<IPointageProfesseur> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPointageProfesseur[]>): HttpResponse<IPointageProfesseur[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
