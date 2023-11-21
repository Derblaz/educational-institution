import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Table } from 'primeng/table';
import { Course, Pagination, Search } from 'src/app/models';
import { CourseService } from 'src/app/services/course.service';
import { RefreshObserverService } from 'src/app/services/refresh-observer.service';

@Component({
  selector: 'course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css'],
})
export class CourseListComponent implements OnInit {
  @Input() editable: boolean = false;
  @Input() course: Course[] | undefined = undefined;
  @Output() newEvent = new EventEmitter<undefined>();
  @Output() editEvent = new EventEmitter<Course>();
  @Output() deleteEvent = new EventEmitter<Course>();

  @ViewChild('dt')
  table!: Table;

  pagination: Pagination<Course> = {
    perPage: 2,
  } as Pagination<Course>;

  filterValue: string = '';

  isUpdate = false;

  selectedDiscipline: Course = {} as Course;

  constructor(
    private courseService: CourseService,
    private refresh: RefreshObserverService
  ) {}

  ngOnInit(): void {
    this.refresh.observable().subscribe((event: string) => {
      const filter: Search = {};

      filter.page = this.pagination.currentPage;
      filter.perPage = this.pagination.perPage;
      if (event == 'CURSO') {
        this.table.reset();
      }
    });
  }

  loadCourses(event?: any) {
    if (this.course) {
      this.pagination = {
        currentPage: 1,
        perPage: this.course.length,
        total: this.course.length,
        items: this.course,
      } as Pagination<Course>;
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

    this.courseService.listCourses(filter).subscribe(
      (resp) => (this.pagination = resp),
      (errorRespose) => console.log(errorRespose.error.message)
    );
    console.log('teste');
  }

  deleteHandler(course: Course) {
    console.log('Delete List');
    this.courseService
      .deleteCourse(course)
      .subscribe((_resp) => this.table.reset());
  }
}
