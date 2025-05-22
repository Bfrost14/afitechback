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
import { NewPointageProfesseur } from '../pointage-professeur.model';
import { PointageProfesseurService } from '../service/pointage-professeur.service';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-pointage-professeur',
    styleUrls: ['./liste-pointage-professeur.component.scss'],
    templateUrl: './liste-pointage-professeur.component.html',
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
export class ListePointageProfesseurComponent implements OnInit, AfterViewInit {
    searchFieldList: SearchFild[] = [
        { key: 'professeur', value: null },
    ];

    dataSource: NewPointageProfesseur[] = [];
    columnsToDisplay = [ 'professeur','heureArrivee', 'heureDepart'];
    displayedColumn: string[] = ['professeur','heureArrivee', 'heureDepart'];
    displayedColumns: string[] = ['professeur','heureArrivee', 'heureDepart'];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewPointageProfesseur | null;
    selectPointageProfesseurEdit: NewPointageProfesseur
    selectedColumn = [
        { key: 'professeur', value: '' },
        { key: 'dateDebut', value: '' },
        { key: 'dateFin', value: '' },
    ];
    add: boolean = false
    edit: boolean = false
    pointageId: number = 0
    pointageIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewPointageProfesseur[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _pointageService: PointageProfesseurService,
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
        this.sort.sort({ id: 'professeur', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allPointageProfesseurs();
    }

    getAllPointageProfesseur(column: SearchFild[]) {
        let professeur = '';
        let dateDebut = '';
        let dateFin = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'professeur':
                    professeur = col.value;
                    break;
                case 'dateDebut':
                    dateDebut = new Date(col.value).toISOString();;
                    break;
                case 'dateFin':
                    dateFin = new Date(col.value).toISOString();;
                    break;

                default:
            }
        });
        return this._pointageService
            .query(
                {page: this.paginator.pageIndex, 
                    size: this.paginator.pageSize, 
                    sort: this.sort.active+","+this.sort.direction, professeur: professeur, dateDebut: dateDebut, dateFin: dateFin },
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ pointage data @@@@@@@@@@@@',
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
            this.getAllPointageProfesseur(fields);
            
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
        this.getAllPointageProfesseur(column);
      
    }

    allPointageProfesseurs() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._pointageService!.query(
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
                        '@@@@@@@@@@@@@@@@@ pointage data @@@@@@@@@@@@@@@@',
                        data
                    );

                    this.resultsLength = data.body.pagination.length;
                    return data.body.data;
                })
            )
            .subscribe((data) => {
                data.forEach((value: any) => {
                    if(String(value.heureDepart).split("-",1)[0] == "1970"){
                        value.heureDepart = null;
                    }
                })
                this.data = data;
                this.dataSource = [...data];
            });
    }



    refreshColumnToDisplay(event) {
        const afficher = event.value;
        //  this.displayedColumns = afficher
        this.columnsToDisplayWithExpand = [
            'professeur',
            ...afficher,
            'expand',
        ];
    }

    fiche: boolean = false;
    setDataSource(pointage: NewPointageProfesseur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', pointage);
        if (this.fiche) {
            this.pointageId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.pointageId = pointage.id
            this.fiche = true;
            this.data = [pointage];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(pointage: NewPointageProfesseur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', pointage);
        if (this.add) {
            this.pointageIdEdit = null
            this.selectPointageProfesseurEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.pointageIdEdit = pointage.id
            this.selectPointageProfesseurEdit = pointage
            this.add = true;
            this.data = [pointage];
        }
    }

    setAdd(event){
        this.add = event
        this.allPointageProfesseurs()
      }
}
