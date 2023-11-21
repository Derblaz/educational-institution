import { Component } from '@angular/core';
import {
  DialogService,
  DynamicDialogConfig,
  DynamicDialogRef,
} from 'primeng/dynamicdialog';
import { Discipline, Search } from 'src/app/models';
import { DisciplineService } from 'src/app/services/discipline.service';

@Component({
  selector: 'app-discipline-form',
  templateUrl: './discipline-form.component.html',
  providers: [DialogService],
  styleUrls: ['./discipline-form.component.css'],
})
export class DisciplineFormComponent {
  discipline: Discipline;
  editable: boolean = false;
  filteredDiscipline: any[] = [];

  constructor(
    private dialogService: DynamicDialogConfig,
    private dialogRef: DynamicDialogRef,
    private disciplineService: DisciplineService
  ) {
    this.discipline = JSON.parse(JSON.stringify(dialogService.data.discipline));
    this.editable = dialogService.data.discipline.id ? true : false;
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

  save() {
    if (this.editable) {
      this.disciplineService.updateDiscipline(this.discipline).subscribe(
        (_resp) => {
          this.close();
        },
        (errorRespose) => console.log(errorRespose.error.message)
      );
    } else {
      this.disciplineService.createDiscipline(this.discipline).subscribe(
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
    this.discipline = {};
    this.dialogRef.close();
  }
}
