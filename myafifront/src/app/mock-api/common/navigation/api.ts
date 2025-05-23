import { Injectable } from '@angular/core';
import { cloneDeep } from 'lodash-es';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { FuseMockApiService } from '@fuse/lib/mock-api';
import { defaultNavigation } from 'app/mock-api/common/navigation/data';
import { AuthService } from 'app/core/auth/auth.service';

@Injectable({
    providedIn: 'root'
})
export class NavigationMockApi {

    /**
     * Constructor
     */
    constructor(private _fuseMockApiService: FuseMockApiService, private _authService: AuthService) {
        // Register Mock API handlers
        this.registerHandlers();
    }
    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register Mock API handlers
     */
    registerHandlers(): void {
        // -----------------------------------------------------------------------------------------------------
        // @ Navigation - GET
        // -----------------------------------------------------------------------------------------------------
        this._fuseMockApiService
            .onGet('api/common/navigation')
            .reply(() => {
                let liste: any[] = [];
                const roleUtilisateur = this._authService.getUtilisateur().role;

                defaultNavigation.forEach(nav => {
                    // Vérifier les enfants autorisés
                    let enfantsAutorises = [];

                    if (nav.children && nav.children.length > 0) {
                        enfantsAutorises = nav.children.filter(child =>
                            roleUtilisateur.includes(child.authority)
                        );
                    }

                    // Vérifier si le parent est autorisé OU s'il a au moins un enfant autorisé
                    if (roleUtilisateur.includes(nav.authority) || enfantsAutorises.length > 0) {
                        // Ajouter le groupe avec seulement les enfants autorisés
                        const itemAvecEnfantsFiltres = {
                            ...nav,
                            children: enfantsAutorises.length > 0 ? enfantsAutorises : undefined
                        };
                        liste.push(itemAvecEnfantsFiltres);
                    }
                });


                // Return the response
                return [
                    200,
                    {
                        default: cloneDeep(liste)
                    }
                ];
            });
    }

}
