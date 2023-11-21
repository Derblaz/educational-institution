import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course, Pagination, Search } from '../models';
import { env } from '../env';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private base_url = env.apiUrl + '/courses';

  constructor(private http: HttpClient) {}

  public createCourse(course: Course) {
    const semesters: any = course.semesters?.map((semester) =>
      semester.map((discipline) => discipline.id?.value)
    );
    return this.http
      .post<{ id: string }>(this.base_url, {
        name: course.name,
        monthsDuration: course.monthsDuration,
        places: course.places,
        description: course.description,
        semesters: semesters,
      })
      .pipe();
  }

  public updateCourse(course: Course) {
    const id = course.id?.value;
    const semesters: any = course.semesters?.map((semester) =>
      semester.map((discipline) => discipline.id?.value)
    );
    return this.http.put<{ id: string }>(this.base_url + '/' + id, {
      name: course.name,
      monthsDuration: course.monthsDuration,
      places: course.places,
      description: course.description,
      semesters: semesters,
    });
  }

  public listCourses(search?: Search) {
    const params: any = search;
    return this.http.get<Pagination<Course>>(this.base_url, { params });
  }

  public deleteCourse(course: Course) {
    const id = course.id?.value;
    return this.http.delete(this.base_url + '/' + id).pipe();
  }
}
