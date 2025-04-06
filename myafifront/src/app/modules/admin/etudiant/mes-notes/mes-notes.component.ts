import {
    ViewChild,
    AfterViewInit,
    Component,
    OnInit,
    Input,
} from '@angular/core';
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
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import {
    FormArray,
    UntypedFormBuilder,
    UntypedFormGroup,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Note } from '../../note/note';
import { NoteService } from '../../note/note.service';
import { AuthService } from 'app/core/auth/auth.service';
import { UserAll } from '../user-all';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-mes-notes',
    styleUrls: ['./mes-notes.component.css'],
    templateUrl: './mes-notes.component.html',
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
export class MesNotesComponent implements OnInit, AfterViewInit {
    @Input() userEmail: string;
    @Input() user: UserAll;
    searchFieldList: SearchFild[] = [
        { key: 'matiere', value: null },
        { key: 'semestre', value: null },
    ];

    dataSource: Note[] = [];
    columnsToDisplay = ['matiere', 'semestre', 'valeur'];
    displayedColumn: string[] = ['semestre', 'valeur'];
    displayedColumns: string[] = ['semestre', 'valeur'];

    expandedElement: Note | null;
    selectNoteEdit: Note;
    selectedColumn = [
        { key: 'matiere', value: '' },
        { key: 'semestre', value: '' },
    ];
    columnsToDisplayWithExpand = [...this.columnsToDisplay];
    add: boolean = false;
    edit: boolean = false;
    noteId: number = 0;
    noteIdEdit: number = 0;
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
        private _authService: AuthService
    ) {}

    ngOnInit(): void {
      if(this._authService.getUtilisateur().role == "ROLE_SECRETAIRE"){
        this.columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
      }
        this.searchColumnForm = this._formBuilder.group({
            searchFilds: this._formBuilder.array([]),
        });
        this.searchFilds = this.searchColumnForm.get(
            'searchFilds'
        ) as FormArray;
        
        if(this.userEmail == undefined){
          this.userEmail = this._authService.getUtilisateur().email;
        }
    }

    ngAfterViewInit() {
        this.addSearchFiels(this.selectedColumn);
        this.sort.sort({ id: 'matiere', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allNotes();
    }

    getAllNote(column: SearchFild[]) {
        let matiere = '';
        let semestre = '';
        column.forEach((col) => {
            switch (col.key) {
                case 'matiere':
                    matiere = col.value;
                    break;
                case 'semestre':
                    semestre = col.value;
                    break;

                default:
            }
        });
        return this._noteService
            .getnoteEtudiants(
                this.paginator.pageIndex,
                this.paginator.pageSize,
                this.sort.active,
                this.sort.direction,
                this.userEmail,
                matiere,
                semestre
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
                        value: [field.value],
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
                    return this._noteService!.getnoteEtudiants(
                        this.paginator.pageIndex,
                        this.paginator.pageSize,
                        this.sort.active,
                        this.sort.direction,
                        this.userEmail
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
       
        if(this._authService.getUtilisateur().role == "ROLE_SECRETAIRE"){
          this.columnsToDisplayWithExpand = ['matiere', ...afficher, 'expand'];
        }else{
          this.columnsToDisplayWithExpand = ['matiere', ...afficher];
        }
    }

    fiche: boolean = false;
    setDataSource(note: Note) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', note);
        if (this.fiche) {
            this.noteId = null;
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.noteId = note.id;
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
            this.noteIdEdit = null;
            this.selectNoteEdit = null;
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.noteIdEdit = note.id;
            this.selectNoteEdit = note;
            this.add = true;
            this.data = [note];
        }
    }

    setAdd(event) {
        this.add = event;
        this.allNotes();
    }
}
