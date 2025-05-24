import { ViewChild, AfterViewInit, Component, OnInit } from '@angular/core';
import {
    animate,
    state,
    style,
    transition,
    trigger,
} from '@angular/animations';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortable } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import {
    catchError,
    map,
    startWith,
    switchMap,
} from 'rxjs/operators';
import {
    FormArray,
    UntypedFormBuilder,
    UntypedFormGroup,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NewProfile } from '../profile.model';
import { ProfileService } from '../service/profile.service';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-profile',
    styleUrls: ['./liste-profile.component.scss'],
    templateUrl: './liste-profile.component.html',
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({ height: '0px', minHeight: '0' })),
            state('expanded', style({ height: '*' })),
            transition(
                'expanded <=> collapsed',
                animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
            ),
        ]),
    ],
})
export class ListeProfileComponent implements OnInit, AfterViewInit {
    searchFieldList: SearchFild[] = [
        { key: 'nom', value: null },
    ];

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

    dataSource: NewProfile[] = [];
    columnsToDisplay = [ 'nom', "pageredirection", "typeProfil"];
    displayedColumn: string[] = ['nom', "pageredirection", "typeProfil"];
    displayedColumns: string[] = ['nom', "pageredirection", "typeProfil"];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewProfile | null;
    selectProfileEdit: NewProfile
    selectedColumn = [
        { key: 'nom', value: '' },
    ];
    add: boolean = false
    edit: boolean = false
    profileId: number = 0
    profileIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewProfile[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _profileService: ProfileService,
        private _formBuilder: UntypedFormBuilder,
        private _route: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.searchColumnForm = this._formBuilder.group({
            searchFilds: this._formBuilder.array([]),
        });
        this.searchFilds = this.searchColumnForm.get(
            'searchFilds'
        ) as FormArray;
    }

    ngAfterViewInit() {
        this.addSearchFiels(this.selectedColumn);
        this.sort.sort({ id: 'nom', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allProfiles();
    }

    getAllProfile(column: SearchFild[]) {
  
        let nom = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'nom':
                    nom = col.value;
                    break;

                default:
            }
        });
        return this._profileService
            .query({page: this.paginator.pageIndex, 
                size: this.paginator.pageSize, 
                sort: this.sort.active+","+this.sort.direction, nom: nom}
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ profile data @@@@@@@@@@@@',
                    data
                );

                this.resultsLength = data.body.pagination.length;
                this.data = data.body.data;
                this.dataSource = [...data.body.data];
            });
    }

    removeColumn(event) {
        const afficher = event.value;
        // this.displayedColumns=afficher
        console.log('@@@@@@@@@@@ remove column +++++++++', afficher);
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ search fields @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    addSearchFiels(fields: SearchFild[]) {
        console.log('********* selected field=======', fields);

        console.log('############## form array field=======', fields);

        if (fields.length == 0) {
            this.searchFilds.clear();
            this.getAllProfile(fields);
            
        } else {
            this.searchFilds.clear();
            fields.forEach((field) => {
                this.searchFilds.push(
                    this._formBuilder.group({
                        key: [field.key],
                        value: [field.value]
                    })
                );
            });
        }
    }

    
    public compareWith(object1: SearchFild, object2: SearchFild): boolean {
        return object1?.key === object2?.key;
    }

    search() {
        let column = this.searchFilds.value.filter(
            (col) => col.value != null && col.value != ''
        );
        console.log(
            '@@@@@@@@@@@@@ filtered column ======>',
            this.selectedColumn
        );
        this.getAllProfile(column);
      
    }

    allProfiles() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._profileService!.query(
                        {page: this.paginator.pageIndex, 
                            size: this.paginator.pageSize, 
                            sort: this.sort.active+","+this.sort.direction}
                    ).pipe(catchError(() => observableOf(null)));
                }),
                map((data) => {
                    // Flip flag to show that loading has finished.
                    this.isLoadingResults = false;
                    this.isRateLimitReached = data === null;

                    if (data === null) {
                        return [];
                    }

                    // Only refresh the result length if there is new data. In case of rate
                    // limit errors, we do not want to reset the paginator to zero, as that
                    // would prevent users from re-triggering requests.
                    console.log(
                        '@@@@@@@@@@@@@@@@@ profile data @@@@@@@@@@@@@@@@',
                        data
                    );

                    this.resultsLength = data.body.pagination.length;
                    return data.body.data;
                })
            )
            .subscribe((data) => {
                this.data = data;
                this.dataSource = [...data];
            });
    }



    refreshColumnToDisplay(event) {
        const afficher = event.value;
        //  this.displayedColumns = afficher
        this.columnsToDisplayWithExpand = [
            'nom',
            ...afficher,
            'expand',
        ];
    }

    fiche: boolean = false;
    setDataSource(profile: NewProfile) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', profile);
        if (this.fiche) {
            this.profileId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.profileId = profile.id
            this.fiche = true;
            this.data = [profile];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(profile: NewProfile) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', profile);
        if (this.add) {
            this.profileIdEdit = null
            this.selectProfileEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.profileIdEdit = profile.id
            this.selectProfileEdit = profile
            this.add = true;
            this.data = [profile];
        }
    }

    setAdd(event){
        this.add = event
        this.allProfiles()
      }

      findPage(link: string){
        return this.allLinks.find(value => value.link == link)?.title || link
      }
}
