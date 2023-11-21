import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Table } from 'primeng/table';
import { Discipline, Pagination, Search } from 'src/app/models';
import { DisciplineService } from 'src/app/services/discipline.service';
import { RefreshObserverService } from 'src/app/services/refresh-observer.service';

@Component({
  selector: 'discipline-list',
  templateUrl: './discipline-list.component.html',
  styleUrls: ['./discipline-list.component.css'],
})
export class DisciplineListComponent implements OnInit {
  @Input() editable: boolean = false;
  @Input() course: Discipline[] | undefined = undefined;
  @Output() newEvent = new EventEmitter<undefined>();
  @Output() editEvent = new EventEmitter<Discipline>();
  @Output() deleteEvent = new EventEmitter<Discipline>();

  @ViewChild('dt')
  table!: Table;

  pagination: Pagination<Discipline> = {
    perPage: 2,
  } as Pagination<Discipline>;

  filterValue: string = '';

  isUpdate = false;

  selectedDiscipline: Discipline = {} as Discipline;

  constructor(
    private disciplineService: DisciplineService,
    private refresh: RefreshObserverService
  ) {}

  ngOnInit(): void {
    this.refresh.observable().subscribe((event: string) => {
      const filter: Search = {};

      filter.page = this.pagination.currentPage;
      filter.perPage = this.pagination.perPage;
      if (event == 'DISCIPLINE') {
        this.table.reset();
      }
    });
  }

  loadDisciplines(event?: any) {
    if (this.course) {
      this.pagination = {
        currentPage: 1,
        perPage: this.course.length,
        total: this.course.length,
        items: this.course,
      } as Pagination<Discipline>;
      return;
    }
    const filter: Search = {};

    if (event) {
      const first = event.first;
      const perPage = event.rows;
      const page = Math.floor(first / perPage);

      filter.page = page;
      filter.perPage = perPage;
    }

    if (this.filterValue.length > 0) filter.search = this.filterValue;

    this.disciplineService.listDisciplines(filter).subscribe(
      (resp) => (this.pagination = resp),
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  deleteHandler(discipline: Discipline) {
    console.log('Delete List');
    this.disciplineService
      .deleteDiscipline(discipline)
      .subscribe((_resp) => this.table.reset());
  }
}
