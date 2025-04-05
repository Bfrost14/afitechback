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
import { NoteService } from '../note.service';
import { Note } from '../note';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-note',
    styleUrls: ['./liste-note.component.css'],
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
        { key: 'matiere', value: null },
        { key: 'prenom', value: null },
        { key: 'nom', value: null },
        { key: 'semestre', value: null },
        { key: 'filiere', value: null },
    ];

    dataSource: Note[] = [];
    columnsToDisplay = ["matricule", 'prenom', 'nom', 'filiere',"matiere", 'semestre',"valeur"];
    displayedColumn: string[] = ['prenom', 'nom', 'filiere',"matiere", 'semestre',"valeur"];
    displayedColumns: string[] = ['prenom', 'nom', 'filiere',"matiere", 'semestre',"valeur"];

    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: Note | null;
    selectNoteEdit: Note
    selectedColumn = [
        { key: 'matiere', value: '' },
        { key: 'semestre', value: '' },
        { key: 'filiere', value: '' },
    ];
    add: boolean = false
    edit: boolean = false
    noteId: number = 0
    noteIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: Note[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _noteService: NoteService,
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
        this.sort.sort({ id: 'matiere', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allNotes();
    }

    getAllNote(column: SearchFild[]) {
        let matiere = '';
        let prenom = '';
        let nom = '';
        let filiere = '';
        let semestre = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'matiere':
                    matiere = col.value;
                    break;
                case 'prenom':
                    prenom = col.value;
                    break;
                case 'nom':
                    nom = col.value;
                    break;
                case 'filiere':
                    filiere = col.value;
                    break;
                case 'semestre':
                    semestre = col.value;
                    break;

                default:
            }
        });
        return this._noteService
            .getnote(
                this.paginator.pageIndex,
                this.paginator.pageSize,
                this.sort.active,
                this.sort.direction,
                matiere,
                semestre,
                nom,
                prenom,
                filiere
            )
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ note data @@@@@@@@@@@@',
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
            this.getAllNote(fields);
            
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
        this.getAllNote(column);
      
    }

    allNotes() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._noteService!.getnote(
                        this.paginator.pageIndex,
                        this.paginator.pageSize,
                        this.sort.active,
                        this.sort.direction
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
                    // would prevent notes from re-triggering requests.
                    console.log(
                        '@@@@@@@@@@@@@@@@@ note data @@@@@@@@@@@@@@@@',
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
    setDataSource(note: Note) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', note);
        if (this.fiche) {
            this.noteId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.noteId = note.id
            this.fiche = true;
            this.data = [note];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(note: Note) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', note);
        if (this.add) {
            this.noteIdEdit = null
            this.selectNoteEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.noteIdEdit = note.id
            this.selectNoteEdit = note
            this.add = true;
            this.data = [note];
        }
    }

    setAdd(event){
        this.add = event
        this.allNotes()
      }
}
