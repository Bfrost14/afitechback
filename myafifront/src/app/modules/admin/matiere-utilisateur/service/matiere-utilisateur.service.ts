import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatiereUtilisateur, NewMatiereUtilisateur } from '../matiere-utilisateur.model';

export type PartialUpdateMatiereUtilisateur = Partial<IMatiereUtilisateur> & Pick<IMatiereUtilisateur, 'id'>;

export type EntityResponseType = HttpResponse<IMatiereUtilisateur>;
export type EntityArrayResponseType = HttpResponse<IMatiereUtilisateur[]>;

@Injectable({ providedIn: 'root' })
export class MatiereUtilisateurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/matiere-utilisateurs');

  create(matiereUtilisateur: NewMatiereUtilisateur): Observable<any> {
    return this.http.post<IMatiereUtilisateur>(this.resourceUrl, matiereUtilisateur, { observe: 'response' });
  }

  update(matiereUtilisateur: IMatiereUtilisateur): Observable<any> {
    return this.http.put<IMatiereUtilisateur>(
      `${this.resourceUrl}/${this.getMatiereUtilisateurIdentifier(matiereUtilisateur)}`,
      matiereUtilisateur,
      { observe: 'response' },
    );
  }

  partialUpdate(matiereUtilisateur: PartialUpdateMatiereUtilisateur): Observable<any> {
    return this.http.patch<IMatiereUtilisateur>(
      `${this.resourceUrl}/${this.getMatiereUtilisateurIdentifier(matiereUtilisateur)}`,
      matiereUtilisateur,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<any> {
    return this.http.get<IMatiereUtilisateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<IMatiereUtilisateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMatiereUtilisateurIdentifier(matiereUtilisateur: Pick<IMatiereUtilisateur, 'id'>): number {
    return matiereUtilisateur.id;
  }

  compareMatiereUtilisateur(o1: Pick<IMatiereUtilisateur, 'id'> | null, o2: Pick<IMatiereUtilisateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getMatiereUtilisateurIdentifier(o1) === this.getMatiereUtilisateurIdentifier(o2) : o1 === o2;
  }

  addMatiereUtilisateurToCollectionIfMissing<Type extends Pick<IMatiereUtilisateur, 'id'>>(
    matiereUtilisateurCollection: Type[],
    ...matiereUtilisateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const matiereUtilisateurs: Type[] = matiereUtilisateursToCheck.filter(isPresent);
    if (matiereUtilisateurs.length > 0) {
      const matiereUtilisateurCollectionIdentifiers = matiereUtilisateurCollection.map(matiereUtilisateurItem =>
        this.getMatiereUtilisateurIdentifier(matiereUtilisateurItem),
      );
      const matiereUtilisateursToAdd = matiereUtilisateurs.filter(matiereUtilisateurItem => {
        const matiereUtilisateurIdentifier = this.getMatiereUtilisateurIdentifier(matiereUtilisateurItem);
        if (matiereUtilisateurCollectionIdentifiers.includes(matiereUtilisateurIdentifier)) {
          return false;
        }
        matiereUtilisateurCollectionIdentifiers.push(matiereUtilisateurIdentifier);
        return true;
      });
      return [...matiereUtilisateursToAdd, ...matiereUtilisateurCollection];
    }
    return matiereUtilisateurCollection;
  }
}
