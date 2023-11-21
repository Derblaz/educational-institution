import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { AdministratorComponent } from './components/administrator/administrator.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { DisciplineListComponent } from './components/discipline-list/discipline-list.component';
import { StudentComponent } from './components/student/student.component';
import { CoordinatorComponent } from './components/coordinator/coordinator.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { CourseListComponent } from './components/course-list/course-list.component';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { CourseFormComponent } from './components/course-form/course-form.component';
import { DisciplineFormComponent } from './components/discipline-form/discipline-form.component';
import { TeacherComponent } from './components/teacher/teacher.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdministratorComponent,
    DisciplineListComponent,
    StudentComponent,
    CoordinatorComponent,
    CourseListComponent,
    CourseFormComponent,
    DisciplineFormComponent,
    TeacherComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    FormsModule,
    PanelModule,
    TableModule,
    AutoCompleteModule,
    DynamicDialogModule,
  ],
  providers: [DialogService],
  bootstrap: [AppComponent],
})
export class AppModule {}
