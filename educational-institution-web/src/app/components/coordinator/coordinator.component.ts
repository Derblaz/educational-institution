import { Component, EventEmitter, Output } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CourseListComponent } from '../course-list/course-list.component';
import { CourseFormComponent } from '../course-form/course-form.component';
import { Course, Discipline } from 'src/app/models';
import { RefreshObserverService } from 'src/app/services/refresh-observer.service';
import { CourseService } from 'src/app/services/course.service';
import { DisciplineFormComponent } from '../discipline-form/discipline-form.component';

@Component({
  selector: 'app-coordinator',
  templateUrl: './coordinator.component.html',
  providers: [DialogService],
  styleUrls: ['./coordinator.component.css'],
})
export class CoordinatorComponent {
  page: string = 'CURSO';
  ref: DynamicDialogRef | undefined;

  constructor(
    public dialogService: DialogService,
    private refresh: RefreshObserverService
  ) {}

  toPage(page: string) {
    this.page = page;
  }

  newCourseHandler() {
    this.ref = this.dialogService.open(CourseFormComponent, {
      width: '80%',
      data: {
        course: {} as Course,
      },
      header: 'Criar Curso',
    });

    this.ref.onClose.subscribe(() => {
      console.log('refresh');
      this.refresh.emit('CURSO');
    });
  }

  editCourseHandler(course: Course) {
    this.ref = this.dialogService.open(CourseFormComponent, {
      width: '80%',
      data: {
        course,
      },
      header: 'Editar Curso',
    });

    this.ref.onClose.subscribe(() => {
      this.refresh.emit('CURSO');
    });
  }

  newDisciplineHandler() {
    this.ref = this.dialogService.open(DisciplineFormComponent, {
      width: '80%',
      data: {
        discipline: {} as Discipline,
      },
      header: 'Criar Disciplina',
    });

    this.ref.onClose.subscribe(() => {
      this.refresh.emit('DISCIPLINE');
    });
  }

  editDisciplineHandler(discipline: Discipline) {
    this.ref = this.dialogService.open(DisciplineFormComponent, {
      width: '80%',
      data: {
        discipline,
      },
      header: 'Editar Disciplina',
    });

    this.ref.onClose.subscribe(() => {
      this.refresh.emit('DISCIPLINE');
    });
  }
}
