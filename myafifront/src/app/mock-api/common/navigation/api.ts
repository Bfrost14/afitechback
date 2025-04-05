import { Injectable } from '@angular/core';
import { cloneDeep } from 'lodash-es';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { FuseMockApiService } from '@fuse/lib/mock-api';
import { defaultNavigation } from 'app/mock-api/common/navigation/data';
import { AuthService } from 'app/core/auth/auth.service';

@Injectable({
    providedIn: 'root'
})
export class NavigationMockApi
{

    /**
     * Constructor
     */
    constructor(private _fuseMockApiService: FuseMockApiService, private _authService: AuthService)
    {
        // Register Mock API handlers
        this.registerHandlers();
    }
    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register Mock API handlers
     */
    registerHandlers(): void
    {
        // -----------------------------------------------------------------------------------------------------
        // @ Navigation - GET
        // -----------------------------------------------------------------------------------------------------
        this._fuseMockApiService
            .onGet('api/common/navigation')
            .reply(() => {
                let liste : any = []
                if(this._authService.getUtilisateur().role == "ROLE_ETUDIANT"){
                    liste = defaultNavigation.filter(def => def.id.includes('etudiant.'))
                }else{
                    liste = defaultNavigation.filter(def => def.id.includes('secretaire.'))
                }
                
                // Return the response
                return [
                    200,
                    {
                        default   : cloneDeep(liste )
                    }
                ];
            });
    }

}
