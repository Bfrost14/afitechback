import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { IAbsence, NewAbsence } from '../models/absence.model';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { isPresent } from 'app/core/util/operators';
import { createRequestOption } from 'app/core/request/request-util';
import { DATE_FORMAT } from 'app/core/config/input.constants';

export type PartialUpdateAbsence = Partial<IAbsence> & Pick<IAbsence, 'id'>;

type RestOf<T extends IAbsence | NewAbsence> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestAbsence = RestOf<IAbsence>;

export type NewRestAbsence = RestOf<NewAbsence>;

export type PartialUpdateRestAbsence = RestOf<PartialUpdateAbsence>;

export type EntityResponseType = HttpResponse<IAbsence>;
export type EntityArrayResponseType = HttpResponse<IAbsence[]>;

@Injectable({ providedIn: 'root' })
export class AbsenceService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/absences');

  create(absence: NewAbsence[]): Observable<any> {
    return this.http
      .post<RestAbsence>(this.resourceUrl, absence, { observe: 'response' })
  }

  update(absence: IAbsence): Observable<any> {
    return this.http
      .put<RestAbsence>(`${this.resourceUrl}/${this.getAbsenceIdentifier(absence)}`, absence, { observe: 'response' })
  }

  partialUpdate(absence: PartialUpdateAbsence): Observable<any> {
    const copy = this.convertDateFromClient(absence);
    return this.http
      .patch<RestAbsence>(`${this.resourceUrl}/${this.getAbsenceIdentifier(absence)}`, copy, { observe: 'response' })
  }

  find(id: number): Observable<any> {
    return this.http
      .get<RestAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAbsence[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAbsenceIdentifier(absence: Pick<IAbsence, 'id'>): number {
    return absence.id;
  }

  compareAbsence(o1: Pick<IAbsence, 'id'> | null, o2: Pick<IAbsence, 'id'> | null): boolean {
    return o1 && o2 ? this.getAbsenceIdentifier(o1) === this.getAbsenceIdentifier(o2) : o1 === o2;
  }

  addAbsenceToCollectionIfMissing<Type extends Pick<IAbsence, 'id'>>(
    absenceCollection: Type[],
    ...absencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const absences: Type[] = absencesToCheck.filter(isPresent);
    if (absences.length > 0) {
      const absenceCollectionIdentifiers = absenceCollection.map(absenceItem => this.getAbsenceIdentifier(absenceItem));
      const absencesToAdd = absences.filter(absenceItem => {
        const absenceIdentifier = this.getAbsenceIdentifier(absenceItem);
        if (absenceCollectionIdentifiers.includes(absenceIdentifier)) {
          return false;
        }
        absenceCollectionIdentifiers.push(absenceIdentifier);
        return true;
      });
      return [...absencesToAdd, ...absenceCollection];
    }
    return absenceCollection;
  }

  protected convertDateFromClient<T extends IAbsence | NewAbsence | PartialUpdateAbsence>(absence: T): RestOf<T> {
    return {
      ...absence,
      date: absence.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAbsence: RestAbsence): IAbsence {
    return {
      ...restAbsence,
      date: restAbsence.date ? dayjs(restAbsence.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAbsence>): HttpResponse<IAbsence> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAbsence[]>): HttpResponse<IAbsence[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
