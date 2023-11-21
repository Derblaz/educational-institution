import { Component } from '@angular/core';
import { Course, Search } from 'src/app/models';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.css'],
})
export class TeacherComponent {
  selectedCourse: Course | undefined;
  filteredCourses: any[] = [];

  constructor(private courseService: CourseService) {}

  filterCourse(event: any) {
    const search: Search = {
      search: event.query,
      page: 0,
      perPage: 5,
      sort: 'name',
    };

    this.courseService.listCourses(search).subscribe(
      (resp) => {
        this.filteredCourses = resp.items;
      },
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  print(event: any) {
    console.log('print ', event);
  }

  eventPrint() {
    console.log(this.selectedCourse);
  }
}
