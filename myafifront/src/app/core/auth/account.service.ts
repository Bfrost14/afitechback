import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, ReplaySubject, of } from 'rxjs';
import { catchError, shareReplay, tap } from 'rxjs/operators';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Account } from 'app/core/auth/account.model';
import { ApplicationConfigService } from '../config/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private readonly http = inject(HttpClient);
  private readonly stateStorageService = inject(StateStorageService);
  private readonly router = inject(Router);
  private readonly applicationConfigService = inject(ApplicationConfigService);

     /**
       * Forgot password
       *
       * @param email
       */
      activate(email: string): Observable<any> {
          return this.http.get(this.applicationConfigService.getEndpointFor("/api/accounts") + '/activate?email='+email);
      }

       /**
       * Forgot password
       *
       * @param email
       */
      resetPassword(email: string): Observable<any> {
          return this.http.get(this.applicationConfigService.getEndpointFor("/api/accounts") + '/change-password?email='+email);
      }
  
    }
