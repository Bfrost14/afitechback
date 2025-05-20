import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/config/application-config.service';
import { IProfile, NewProfile } from '../profile.model';
import { createRequestOption } from 'app/core/request/request-util';

export type PartialUpdateProfile = Partial<IProfile> & Pick<IProfile, 'id'>;

export type EntityResponseType = HttpResponse<IProfile>;
export type EntityArrayResponseType = HttpResponse<IProfile[]>;

@Injectable({ providedIn: 'root' })
export class ProfileService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/profiles');

  create(profile: NewProfile): Observable<any> {
    return this.http.post<IProfile>(this.resourceUrl, profile, { observe: 'response' });
  }

  update(profile: IProfile): Observable<any> {
    return this.http.put<IProfile>(`${this.resourceUrl}/${this.getProfileIdentifier(profile)}`, profile, { observe: 'response' });
  }

  partialUpdate(profile: PartialUpdateProfile): Observable<any> {
    return this.http.patch<IProfile>(`${this.resourceUrl}/${this.getProfileIdentifier(profile)}`, profile, { observe: 'response' });
  }

  find(id: number): Observable<any> {
    return this.http.get<IProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<IProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfileIdentifier(profile: Pick<IProfile, 'id'>): number {
    return profile.id;
  }

  compareProfile(o1: Pick<IProfile, 'id'> | null, o2: Pick<IProfile, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfileIdentifier(o1) === this.getProfileIdentifier(o2) : o1 === o2;
  }

  addProfileToCollectionIfMissing<Type extends Pick<IProfile, 'id'>>(profileCollection: Type[], ...profileSToCheck: (Type | null | undefined)[]): Type[] {
    const profileS: Type[] = profileSToCheck.filter(isPresent);
    if (profileS.length > 0) {
      const profileCollectionIdentifiers = profileCollection.map(profileItem => this.getProfileIdentifier(profileItem));
      const profileSToAdd = profileS.filter(profileItem => {
        const profileIdentifier = this.getProfileIdentifier(profileItem);
        if (profileCollectionIdentifiers.includes(profileIdentifier)) {
          return false;
        }
        profileCollectionIdentifiers.push(profileIdentifier);
        return profileS;
      });
      return [...profileSToAdd, ...profileCollection];
    }
    return profileCollection;
  }
}
