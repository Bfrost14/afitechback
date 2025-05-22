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
import { NewAnneeScolaire } from '../annee-scolaire.model';
import { AnneeScolaireService } from '../service/annee-scolaire.service';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-annee-scolaire',
    styleUrls: ['./liste-annee-scolaire.component.scss'],
    templateUrl: './liste-annee-scolaire.component.html',
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
export class ListeAnneeScolaireComponent implements OnInit, AfterViewInit {
    searchFieldList: SearchFild[] = [
        { key: 'nom', value: null },
    ];

    dataSource: NewAnneeScolaire[] = [];
    columnsToDisplay = [ 'nom'];
    displayedColumn: string[] = ['nom'];
    displayedColumns: string[] = ['nom'];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewAnneeScolaire | null;
    selectAnneeScolaireEdit: NewAnneeScolaire
    selectedColumn = [
        { key: 'nom', value: '' },
    ];
    add: boolean = false
    edit: boolean = false
    anneeScolaireId: number = 0
    anneeScolaireIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewAnneeScolaire[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _anneeScolaireService: AnneeScolaireService,
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

        this.allAnneeScolaires();
    }

    getAllAnneeScolaire(column: SearchFild[]) {
        let nom = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'nom':
                    nom = col.value;
                    break;

                default:
            }
        });
        return this._anneeScolaireService
            .query(
                {page: this.paginator.pageIndex, 
                    size: this.paginator.pageSize, 
                    sort: this.sort.active+","+this.sort.direction, nom: nom}
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ anneeScolaire data @@@@@@@@@@@@',
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
            this.getAllAnneeScolaire(fields);
            
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
        this.getAllAnneeScolaire(column);
      
    }

    allAnneeScolaires() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._anneeScolaireService!.query(
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
                        '@@@@@@@@@@@@@@@@@ anneeScolaire data @@@@@@@@@@@@@@@@',
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
    setDataSource(anneeScolaire: NewAnneeScolaire) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', anneeScolaire);
        if (this.fiche) {
            this.anneeScolaireId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.anneeScolaireId = anneeScolaire.id
            this.fiche = true;
            this.data = [anneeScolaire];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(anneeScolaire: NewAnneeScolaire) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', anneeScolaire);
        if (this.add) {
            this.anneeScolaireIdEdit = null
            this.selectAnneeScolaireEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.anneeScolaireIdEdit = anneeScolaire.id
            this.selectAnneeScolaireEdit = anneeScolaire
            this.add = true;
            this.data = [anneeScolaire];
        }
    }

    setAdd(event){
        this.add = event
        this.allAnneeScolaires()
      }
}
