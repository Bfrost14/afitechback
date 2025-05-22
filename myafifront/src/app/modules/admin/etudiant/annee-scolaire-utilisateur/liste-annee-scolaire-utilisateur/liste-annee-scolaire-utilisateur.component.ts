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
import { ActivatedRoute } from '@angular/router';
import { NewAnneeScolaireUtilisateur } from '../../annee-scolaire-utilisateur.model';
import { AnneeScolaireUtilisateurService } from '../../annee-scolaire-utilisateur.service';
import { NewUtilisateur } from 'app/modules/admin/ue/ue.model';

interface SearchFild {
    key: string;
    value: any;
}

/**
 * @title Table with expandable rows
 */
@Component({
    selector: 'app-liste-annee-scolaire-utilisateur',
    styleUrls: ['./liste-annee-scolaire-utilisateur.component.scss'],
    templateUrl: './liste-annee-scolaire-utilisateur.component.html',
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
export class ListeAnneeScolaireUtilisateurComponent implements OnInit, AfterViewInit {


    dataSource: NewAnneeScolaireUtilisateur[] = [];
    columnsToDisplay = ['anneeScolaire','semestre'];
    displayedColumn: string[] = ['anneeScolaire','semestre'];
    displayedColumns: string[] = ['anneeScolaire','semestre'];
    @Input() user: NewUtilisateur;
    columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
    expandedElement: NewAnneeScolaireUtilisateur | null;
    selectAnneeScolaireUtilisateurEdit: NewAnneeScolaireUtilisateur
    add: boolean = false
    edit: boolean = false
    anneeScolaireUtilisateurId: number = 0
    anneeScolaireUtilisateurIdEdit: number = 0
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    resultsLength = 0;
    isLoadingResults = true;
    isRateLimitReached = false;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    exampleDatabase: any | null;
    data: NewAnneeScolaireUtilisateur[] = [];
    pageSize = 25;
    pageSizeOptions: number[] = [25, 50, 100];
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    searchColumnForm: UntypedFormGroup;
    searchFilds: FormArray;
    /**
     * filtrer par regime
     */

    constructor(
        private _anneeScolaireService: AnneeScolaireUtilisateurService,
        private _formBuilder: UntypedFormBuilder
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
        this.sort.sort({ id: 'anneeScolaire', start: 'asc' } as MatSortable);

        this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

        this.allAnneeScolaireUtilisateurs();
    }



    
    public compareWith(object1: SearchFild, object2: SearchFild): boolean {
        return object1?.key === object2?.key;
    }


    allAnneeScolaireUtilisateurs() {
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                startWith({}),
                switchMap(() => {
                    this.isLoadingResults = true;
                    return this._anneeScolaireService!.query(
                        {page: this.paginator.pageIndex, 
                            size: this.paginator.pageSize, 
                            sort: this.sort.active+","+this.sort.direction, etudiant: this.user.email}
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
    setDataSource(anneeScolaire: NewAnneeScolaireUtilisateur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', anneeScolaire);
        if (this.fiche) {
            this.anneeScolaireUtilisateurId = null
            this.fiche = false;
            this.data = [...this.dataSource];
        } else {
            this.anneeScolaireUtilisateurId = anneeScolaire.id
            this.fiche = true;
            this.data = [anneeScolaire];
        }

        console.log(
            '@@@@@@@@@@@@.. data set after >>>>>>>>>>>>>>>>>>',
            this.dataSource,
            this.fiche
        );
    }

    setDataSourceEdit(anneeScolaire: NewAnneeScolaireUtilisateur) {
        console.log('>>>>>>>>>>>>>>>.. data set >>>>>>>>>>>>>>>>>>', anneeScolaire);
        if (this.add) {
            this.anneeScolaireUtilisateurIdEdit = null
            this.selectAnneeScolaireUtilisateurEdit = null
            this.add = false;
            this.data = [...this.dataSource];
        } else {
            this.anneeScolaireUtilisateurIdEdit = anneeScolaire.id
            this.selectAnneeScolaireUtilisateurEdit = anneeScolaire
            this.add = true;
            this.data = [anneeScolaire];
        }
    }

    setAdd(event){
        this.add = event
        this.allAnneeScolaireUtilisateurs()
      }
}
