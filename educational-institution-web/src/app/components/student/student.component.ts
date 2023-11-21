import { Component } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { Course, Discipline, Search } from 'src/app/models';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css'],
})
export class StudentComponent {
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

  course1: Discipline[] = [
    {
      id: { value: 'kasdasjdflj' },
      name: 'Novo Nome',
      credits: 3.0,
      description: 'string',
      program: 'string',
      presential: true,
      isActive: true,
      createdAt: 'string',
      updatedAt: 'string',
      deletedAt: null,
    },
    {
      id: { value: 'lsdjdfg83' },
      name: 'Casa',
      credits: 3.0,
      description: 'string',
      program: 'string',
      presential: true,
      isActive: true,
      createdAt: 'string',
      updatedAt: 'string',
      deletedAt: null,
    },
    {
      id: { value: 'gdldkjdjkd' },
      name: 'Aviao',
      credits: 3.0,
      description: 'string',
      program: 'string',
      presential: true,
      isActive: true,
      createdAt: 'string',
      updatedAt: 'string',
      deletedAt: null,
    },
    {
      id: { value: 'asdfdfgs' },
      name: 'Novo Nome',
      credits: 3.0,
      description: 'string',
      program: 'string',
      presential: true,
      isActive: true,
      createdAt: 'string',
      updatedAt: 'string',
      deletedAt: null,
    },
    {
      id: { value: 'kasdadfsjdflj' },
      name: 'Novo Nome',
      credits: 3.0,
      description: 'string',
      program: 'string',
      presential: true,
      isActive: true,
      createdAt: 'string',
      updatedAt: 'string',
      deletedAt: null,
    },
  ];
}
