import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnneeScolaireUtilisateur, NewAnneeScolaireUtilisateur } from './annee-scolaire-utilisateur.model';

export type PartialUpdateAnneeScolaireUtilisateur = Partial<IAnneeScolaireUtilisateur> & Pick<IAnneeScolaireUtilisateur, 'id'>;

export type EntityResponseType = HttpResponse<IAnneeScolaireUtilisateur>;
export type EntityArrayResponseType = HttpResponse<IAnneeScolaireUtilisateur[]>;

@Injectable({ providedIn: 'root' })
export class AnneeScolaireUtilisateurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/annee-scolaire-users');

  create(matiereUtilisateur: NewAnneeScolaireUtilisateur[]): Observable<any> {
    return this.http.post<IAnneeScolaireUtilisateur>(this.resourceUrl, matiereUtilisateur, { observe: 'response' });
  }

  update(matiereUtilisateur: IAnneeScolaireUtilisateur): Observable<any> {
    return this.http.put<IAnneeScolaireUtilisateur>(
      `${this.resourceUrl}/${this.getAnneeScolaireUtilisateurIdentifier(matiereUtilisateur)}`,
      matiereUtilisateur,
      { observe: 'response' },
    );
  }

  partialUpdate(matiereUtilisateur: PartialUpdateAnneeScolaireUtilisateur): Observable<any> {
    return this.http.patch<IAnneeScolaireUtilisateur>(
      `${this.resourceUrl}/${this.getAnneeScolaireUtilisateurIdentifier(matiereUtilisateur)}`,
      matiereUtilisateur,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<any> {
    return this.http.get<IAnneeScolaireUtilisateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<IAnneeScolaireUtilisateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnneeScolaireUtilisateurIdentifier(matiereUtilisateur: Pick<IAnneeScolaireUtilisateur, 'id'>): number {
    return matiereUtilisateur.id;
  }

  compareAnneeScolaireUtilisateur(o1: Pick<IAnneeScolaireUtilisateur, 'id'> | null, o2: Pick<IAnneeScolaireUtilisateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnneeScolaireUtilisateurIdentifier(o1) === this.getAnneeScolaireUtilisateurIdentifier(o2) : o1 === o2;
  }

  addAnneeScolaireUtilisateurToCollectionIfMissing<Type extends Pick<IAnneeScolaireUtilisateur, 'id'>>(
    matiereUtilisateurCollection: Type[],
    ...matiereUtilisateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const matiereUtilisateurs: Type[] = matiereUtilisateursToCheck.filter(isPresent);
    if (matiereUtilisateurs.length > 0) {
      const matiereUtilisateurCollectionIdentifiers = matiereUtilisateurCollection.map(matiereUtilisateurItem =>
        this.getAnneeScolaireUtilisateurIdentifier(matiereUtilisateurItem),
      );
      const matiereUtilisateursToAdd = matiereUtilisateurs.filter(matiereUtilisateurItem => {
        const matiereUtilisateurIdentifier = this.getAnneeScolaireUtilisateurIdentifier(matiereUtilisateurItem);
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
