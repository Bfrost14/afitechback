import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormControl } from '@angular/forms';
import { ReplaySubject, Subject } from 'rxjs';
import { take, takeUntil } from 'rxjs/operators';

import { SweetAlertOptions } from 'sweetalert2';
import { AlertToastService } from 'app/core/util/alertToast.service';
import { environment } from 'environments/environment';
import { NewProfile } from '../profile.model';
import { ProfileService } from '../service/profile.service';
import { AuthorityService } from '../../admin/authority/service/authority.service';

@Component({
    selector: 'app-ajout-profile',
    templateUrl: './ajout-profile.component.html',
    styleUrls: ['./ajout-profile.component.scss'],
})
export class AjoutProfileComponent implements OnInit {
    Enregistrer: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Enregistrer !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    Annuler: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Annuler !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
        reverseButtons: true,
    };

    Modifier: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Modifier !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    Supprimer: SweetAlertOptions = {
        title: 'Etes vous sûre?',
        text: 'Supprimer !',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmer',
        cancelButtonText: 'Annuler',
    };

    allLinks = [
        {
            title: 'Pointage',
            link: '/gestionprofesseur/pointage/liste'
        },
        {
            title: 'Absences',
            link: '/gestion/absence/liste'
        },
        {
            title: 'Espace étudiant',
            link: '/gestion/espace'
        },
        {
            title: 'Calendrier de cours',
            link: '/gestion/calendrier/liste'
        },
        {
            title: 'Cours',
            link: '/gestion/cours/liste'
        },
        {
            title: 'Notes',
            link: '/gestion/note/liste'
        },
        {
            title: 'Cahier de texte',
            link: '/gestion/cahiertexte/liste'
        },
        {
            title: 'Notations',
            link: '/gestion/notation/liste'
        },
        {
            title: 'Étudiants',
            link: '/admin/etudiant/liste'
        },
        {
            title: 'Professeurs',
            link: '/admin/professeur/liste'
        },
        {
            title: 'Administrateurs',
            link: '/admin/administration/liste'
        },
        {
            title: 'Affectations matières',
            link: '/admin/affectation/liste'
        },
        {
            title: 'Filières',
            link: '/admin/filiere/liste'
        },
        {
            title: 'Matières',
            link: '/admin/matiere/liste'
        },
        {
            title: 'Unités d\'enseignement',
            link: '/admin/ue/liste'
        },
        {
            title: 'Salles',
            link: '/admin/salle/liste'
        },
        {
            title: 'Campus',
            link: '/admin/campus/liste'
        },
        {
            title: 'Semestres',
            link: '/admin/semestre/liste'
        },
        {
            title: 'Années scolaires',
            link: '/admin/annee/liste'
        },
        {
            title: 'Profils',
            link: '/admin/profiles/liste'
        },
        {
            title: 'Autorisations',
            link: '/admin/authority/liste'
        }
    ];

    form: UntypedFormGroup;
    @Input() profileId: number = 0;
    @Input() profile: NewProfile;
    @Output() edit = new EventEmitter<boolean>();
    @Output() add = new EventEmitter<boolean>();
    @Output() refresh = new EventEmitter<number>();

    // Authority multi-select variables
    authorityFilterCtrl: FormControl = new FormControl();
    filteredAuthorities: ReplaySubject<any[]> = new ReplaySubject<any[]>(1);
    protected _onDestroy = new Subject<void>();
    authorities: any[] = [];

    constructor(
        private _formBuilder: UntypedFormBuilder,
        private _profileService: ProfileService,
        private _alertToastService: AlertToastService,
        private _authoritiesService: AuthorityService
    ) { }

    ngOnInit(): void {
        this.form = this._formBuilder.group({
            id: [null],
            nom: [null, Validators.required],
            redirection: [null, Validators.required],
            authorities: [[], Validators.required],
        });

        if (this.profile != undefined) {
            this.form.patchValue(this.profile);
            //this.form.get("redirection").setValue(this.allLinks.find(value => value.link == this.profile.redirection))
        }

        this.getAllAuthorites();
        this.setupAuthorityFilter();
    }

    ngOnDestroy() {
        this._onDestroy.next();
        this._onDestroy.complete();
    }

    setupAuthorityFilter() {
        this.filteredAuthorities.next(this.authorities.slice());

        this.authorityFilterCtrl.valueChanges
            .pipe(takeUntil(this._onDestroy))
            .subscribe(() => {
                this.filterAuthorities();
            });
    }

    filterAuthorities() {
        if (!this.authorities) {
            return;
        }

        let search = this.authorityFilterCtrl.value;
        if (!search) {
            this.filteredAuthorities.next(this.authorities.slice());
            return;
        } else {
            search = search.toLowerCase();
        }

        this.filteredAuthorities.next(
            this.authorities.filter(authority =>
                authority.name.toLowerCase().indexOf(search) > -1
            )
        );
    }

    getAllAuthorites() {
        this._authoritiesService.query().subscribe(
            response => {
                console.log(response)
                this.authorities = response.body;
                this.filteredAuthorities.next(this.authorities.slice());

                // If editing, set the initial value for authorities
                if (this.profile && this.profile.authorities) {
                    const selectedAuthorities = this.authorities.filter(auth =>
                        this.profile.authorities.some((selectedAuth: any) => selectedAuth.name === auth.name)
                    );
                    this.form.get('authorities').setValue(selectedAuthorities);
                }
            }
        );
    }

    public compareWith(object1: any, object2: any): boolean {
        return object1?.id === object2?.id;
    }

    enregistrer() {
        this.form.value.password = environment.defaultPassword;
        this._profileService.create(this.form.value).subscribe((data) => {
            this.add.emit(false);
            this._alertToastService.toastSuccess('Réussi', 'Ajout profile');
        });
    }

    modifier() {
        this._profileService.update(this.form.value).subscribe((data) => {
            this.add.emit(false);
            this._alertToastService.toastSuccess(
                'Réussie',
                'Modification profile'
            );
        });
    }

    annuler() {
        this.add.emit(false);
    }

    patchValues(valeur) {
        return this._formBuilder.group({
            adresse: [valeur],
        });
    }

    selectionChange(indice) {
        if (indice >= 0) {
            // Your logic here
        }
    }

    supprimer() {
        this._profileService.delete(this.profile.id).subscribe(() => {
            this._alertToastService.toastSuccess(
                'Réussie',
                'Suppression profile'
            );
            this.add.emit(false);
        });
    }
}