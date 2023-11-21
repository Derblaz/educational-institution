import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdministratorComponent } from './components/administrator/administrator.component';
import { DisciplineListComponent } from './components/discipline-list/discipline-list.component';
import { StudentComponent } from './components/student/student.component';
import { CoordinatorComponent } from './components/coordinator/coordinator.component';
import { TeacherComponent } from './components/teacher/teacher.component';

const routes: Routes = [
  { path: '', component: LoginComponent, pathMatch: 'full' },
  { path: 'administrator', component: AdministratorComponent },
  { path: 'coordinator', component: CoordinatorComponent },
  { path: 'teacher', component: TeacherComponent },
  { path: 'student', component: StudentComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
