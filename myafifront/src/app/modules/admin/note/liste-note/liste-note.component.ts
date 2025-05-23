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
import { NewMatiereUtilisateur } from '../../matiere-utilisateur/matiere-utilisateur.model';
import { MatiereUtilisateurService } from '../../matiere-utilisateur/service/matiere-utilisateur.service';
import { AuthService } from 'app/core/auth/auth.service';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-note',
    styleUrls: ['./liste-note.component.scss'],
    templateUrl: './liste-note.component.html',
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
export class ListeNoteComponent implements OnInit, AfterViewInit {
    searchFieldList: SearchFild[] = [
        { key: 'anneeScolaire', value: null },
        { key: 'matiere', value: null },
        { key: 'filiere', value: null },
        { key: 'semestre', value: null },
    ];

    dataSource: NewMatiereUtilisateur[] = [];
    columnsToDisplay = ['anneeScolaire', 'matiere', 'filiere', 'semestre'];
    displayedColumn: string[] = ['anneeScolaire', 'matiere', 'filiere', 'semestre'];
    displayedColumns: string[] = ['anneeScolaire', 'matiere', 'filiere', 'semestre'];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewMatiereUtilisateur | null;
    selectMatiereUtilisateurEdit: NewMatiereUtilisateur
    selectedColumn = [
        { key: 'anneeScolaire', value: null },
        { key: 'matiere', value: null },
    ];
    add: boolean = false
    edit: boolean = false
    matiereUtilisateurId: number = 0
    matiereUtilisateurIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewMatiereUtilisateur[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _matiereService: MatiereUtilisateurService,
        private _formBuilder: UntypedFormBuilder,
        private _authService: AuthService
    ) { }

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
        this.sort.sort({ id: 'anneeScolaire', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allMatiereUtilisateurs();
    }

    getAllMatiereUtilisateur(column: SearchFild[]) {
        let anneeScolaire = '';
        let matiere = '';
        let professeur = '';
        let filiere = '';
        let semestre = '';
        column.forEach((col) => {
            switch (col.key) {

                case 'anneeScolaire':
                    anneeScolaire = col.value;
                    break;
                case 'matiere':
                    matiere = col.value;
                    break;
                case 'filiere':
                    filiere = col.value;
                    break;
                case 'professeur':
                    professeur = col.value;
                    break;
                case 'semestre':
                    semestre = col.value;
                    break;
                default:
            }
        });
        return this._matiereService
            .query(
                {
                    page: this.paginator.pageIndex,
                    size: this.paginator.pageSize,
                    sort: this.sort.active + "," + this.sort.direction,
                     matiere: matiere, professeur: professeur, filiere: filiere,
                      anneeScolaire: anneeScolaire, semestre: semestre
                },
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ matiere data @@@@@@@@@@@@',
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
            this.getAllMatiereUtilisateur(fields);

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
        this.getAllMatiereUtilisateur(column);

    }

    allMatiereUtilisateurs() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._matiereService!.query(
                        {
                            page: this.paginator.pageIndex,
                            size: this.paginator.pageSize,
                            sort: this.sort.active + "," + this.sort.direction
                        }
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
                        '@@@@@@@@@@@@@@@@@ matiere data @@@@@@@@@@@@@@@@',
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
    setDataSource(matiere: NewMatiereUtilisateur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', matiere);
        if (this.fiche) {
            this.matiereUtilisateurId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.matiereUtilisateurId = matiere.id
            this.fiche = true;
            this.data = [matiere];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }


    setAdd(event) {
        this.add = event
        this.allMatiereUtilisateurs()
    }
}
