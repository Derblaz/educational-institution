import { Component } from '@angular/core';
import {
  DialogService,
  DynamicDialogConfig,
  DynamicDialogRef,
} from 'primeng/dynamicdialog';
import { Course, Discipline, Search } from 'src/app/models';
import { CourseService } from 'src/app/services/course.service';
import { DisciplineService } from 'src/app/services/discipline.service';

@Component({
  selector: 'course-form',
  templateUrl: './course-form.component.html',
  providers: [DialogService],
  styleUrls: ['./course-form.component.css'],
})
export class CourseFormComponent {
  course: Course;
  editable: boolean = false;
  filteredDiscipline: any[] = [];

  constructor(
    private dialogService: DynamicDialogConfig,
    private dialogRef: DynamicDialogRef,
    private disciplineService: DisciplineService,
    private courseService: CourseService
  ) {
    this.course = JSON.parse(JSON.stringify(dialogService.data.course));
    this.editable = dialogService.data.course.id ? true : false;
  }

  filterCourse(event: any) {
    const search: Search = {
      search: event.query,
      page: 0,
      perPage: 5,
      sort: 'name',
    };

    this.disciplineService.listDisciplines(search).subscribe(
      (resp) => {
        this.filteredDiscipline = resp.items;
      },
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  addDiscipline(index: number, discipline: Discipline) {
    if (this.course.semesters && discipline) {
      let semester = this.course.semesters[index];
      const exist =
        semester.filter((item) => item.id?.value == discipline.id?.value)
          .length > 0;
      if (!exist) semester.push(discipline);
    }
  }

  deleteDiscipline(index: number, indexDiscipline: number) {
    if (this.course.semesters)
      this.course.semesters[index].splice(indexDiscipline, 1);
  }

  addSemester() {
    console.log(this.course.semesters);
    let semesters = this.course.semesters;
    if (semesters) {
      semesters.push([]);
    } else {
      semesters = [[]];
    }
    this.course.semesters = semesters;
    console.log(this.course.semesters);
  }

  save() {
    if (this.editable) {
      this.courseService.updateCourse(this.course).subscribe(
        (_resp) => {
          this.close();
        },
        (errorRespose) => console.log(errorRespose.error.message)
      );
    } else {
      this.courseService.createCourse(this.course).subscribe(
        (_resp) => {
          this.close();
        },
        (errorRespose) => console.log(errorRespose.error.message)
      );
    }
  }

  cancel() {
    this.close();
  }

  close() {
    this.course = {};
    this.dialogRef.close();
  }
}
