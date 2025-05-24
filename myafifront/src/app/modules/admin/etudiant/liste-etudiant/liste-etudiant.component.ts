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
import { NewUtilisateur } from '../../ue/ue.model';
import { AdminService } from '../../user/service/admin.service';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-etudiant',
    styleUrls: ['./liste-etudiant.component.scss'],
    templateUrl: './liste-etudiant.component.html',
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
export class ListeEtudiantComponent implements OnInit, AfterViewInit {
    searchFieldList: SearchFild[] = [
        { key: 'matricule', value: null },
        { key: 'prenom', value: null },
        { key: 'nom', value: null },
        { key: 'email', value: null },
    ];

    dataSource: NewUtilisateur[] = [];
    columnsToDisplay = ['matricule', 'nom', 'email', 'telephone', 'filiere', 'campus'];
    displayedColumn: string[] = ['nom', 'email', 'telephone', 'filiere', 'campus'];
    displayedColumns: string[] = ['nom', 'email', 'telephone', 'filiere', 'campus'];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewUtilisateur | null;
    selectEtudiantEdit: NewUtilisateur
    selectedColumn = [
        { key: 'email', value: '' },
        { key: 'prenom', value: '' },
        { key: 'nom', value: '' },
    ];
    add: boolean = false
    edit: boolean = false
    etudiantId: string = ""
    etudiantIdEdit: string = ""
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewUtilisateur[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _etudiantService: AdminService,
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
        this.sort.sort({ id: 'matricule', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allEtudiants();
    }

    getAllEtudiant(column: SearchFild[]) {
        let matricule = '';
        let prenom = '';
        let nom = '';
        let email = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'matricule':
                    matricule = col.value;
                    break;
                case 'prenom':
                    prenom = col.value;
                    break;
                case 'nom':
                    nom = col.value;
                    break;
                case 'email':
                    email = col.value;
                    break;

                default:
            }
        });
        return this._etudiantService
            .query(
                 {page: this.paginator.pageIndex, 
                            size: this.paginator.pageSize, 
                            sort: this.sort.active+","+this.sort.direction,
                        email:email, prenom: prenom, nom: nom, matricule: matricule, name: "ETUDIANT" }
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ etudiant data @@@@@@@@@@@@',
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
            this.getAllEtudiant(fields);
            
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
        this.getAllEtudiant(column);
      
    }

    allEtudiants() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    if(this.sort.active == "prenom"){
                        this.sort.active = "firstName"
                    }

                    if(this.sort.active == "nom"){
                        this.sort.active = "lastName"
                    }
                    return this._etudiantService!.query(
                 {page: this.paginator.pageIndex, 
                            size: this.paginator.pageSize, 
                            sort: this.sort.active+","+this.sort.direction, profil: "ETUDIANT"}
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
                        '@@@@@@@@@@@@@@@@@ etudiant data @@@@@@@@@@@@@@@@',
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
            'matricule',
            ...afficher,
            'expand',
        ];
    }

    fiche: boolean = false;
    setDataSource(etudiant: NewUtilisateur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', etudiant);
        if (this.fiche) {
            this.etudiantId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.etudiantId = etudiant.email
            this.fiche = true;
            this.data = [etudiant];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(etudiant: NewUtilisateur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', etudiant);
        if (this.add) {
            this.etudiantIdEdit = null
            this.selectEtudiantEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.etudiantIdEdit = etudiant.email
            this.selectEtudiantEdit = etudiant
            this.add = true;
            this.data = [etudiant];
        }
    }

    setAdd(event){
        this.add = event
        this.allEtudiants()
      }
}

