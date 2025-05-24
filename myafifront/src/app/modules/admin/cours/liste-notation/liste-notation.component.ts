import { ViewChild, AfterViewInit, Component, OnInit, Input } from '@angular/core';
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
import { AdminService } from '../../user/service/admin.service';
import { AbsenceService } from '../service/absence.service';
import { NewNotation } from '../models/notation.model';
import { NotationService } from '../service/notation.service';

interface SearchFild {
  key: string;
  value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
  selector: 'app-liste-notation',
  templateUrl: './liste-notation.component.html',
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

export class ListeNotationComponent implements OnInit, AfterViewInit {

  @Input() cours: any
  @Input() etudiant: any
  dataSource: NewNotation[] = [];
  columnsToDisplay = ['matricule', 'nom', 'note'];
  displayedColumn: string[] = ['nom', 'note'];
  displayedColumns: string[] = ['nom', 'note'];

  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement: NewNotation | null;
  add: boolean = false
  edit: boolean = false
  // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  exampleDatabase: any | null;
  data: NewNotation[] = [];
  pageSize = 25;
  pageSizeOptions: number[] = [25, 50, 100];
  // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  searchColumnForm: UntypedFormGroup;
  searchFilds: FormArray;
  notationId: null;
  /**
   * filtrer par regime
   */

  constructor(
    private _notationService: NotationService,
    private _formBuilder: UntypedFormBuilder
  ) { }

  ngOnInit(): void {
    this.searchColumnForm = this._formBuilder.group({
      searchFilds: this._formBuilder.array([]),
    });
    this.searchFilds = this.searchColumnForm.get(
      'searchFilds'
    ) as FormArray;

    if (this.etudiant != undefined) {
      this.displayedColumns.pop()
      this.displayedColumns.pop()
      this.displayedColumns.pop()
      this.displayedColumns.push("matiere")
      this.displayedColumns.push("dateDebut")
      this.displayedColumns.push("dateFin")
      this.displayedColumns.push("note")
    }
  }

  ngAfterViewInit() {
    this.sort.sort({ id: 'id', start: 'asc' } as MatSortable);

    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    if(this.etudiant != undefined){
      this.allNotationsEtudiant();
    }else{
      this.allNotations();
    }
    
  }



  public compareWith(object1: SearchFild, object2: SearchFild): boolean {
    return object1?.key === object2?.key;
  }
  allNotations() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._notationService!.query(
            {
              page: this.paginator.pageIndex,
              size: 10,
              sort: this.sort.active + "," + this.sort.direction, idCalendrier: this.cours.id
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
            '@@@@@@@@@@@@@@@@@ notation data @@@@@@@@@@@@@@@@',
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

  allNotationsEtudiant() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._notationService!.query(
            {
              page: this.paginator.pageIndex,
              size: 10,
              sort: this.sort.active + "," + this.sort.direction, etudiant: this.etudiant.email
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
            '@@@@@@@@@@@@@@@@@ notation data @@@@@@@@@@@@@@@@',
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

  fiche: boolean = false;


  setAdd(event) {
    this.add = event
    this.allNotations()
  }

    setDataSource(notation: NewNotation) {
          console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', notation);
          if (this.fiche) {
              this.notationId = null
              this.fiche = false;
              this.data = [...this.dataSource];
          } else {
              this.notationId = notation.id
              this.fiche = true;
              this.data = [notation];
          }
  
          console.log(
              '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
              this.dataSource,
              this.fiche
          );
      }
  

}

