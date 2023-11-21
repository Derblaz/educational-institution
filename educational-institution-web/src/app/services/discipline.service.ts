import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'src/app/env';
import { Discipline, Pagination, Search } from 'src/app/models';

@Injectable({
  providedIn: 'root',
})
export class DisciplineService {
  private base_url = env.apiUrl + '/disciplines';

  constructor(private http: HttpClient) {}

  public createDiscipline(discipline: Discipline) {
    return this.http
      .post<{ id: string }>(this.base_url, {
        name: discipline.name,
        credits: discipline.credits,
        description: discipline.description,
        program: discipline.program,
        presential: true,
      })
      .pipe();
  }

  public updateDiscipline(discipline: Discipline) {
    const id = discipline.id?.value;

    return this.http.put<{ id: string }>(this.base_url + '/' + id, {
      name: discipline.name,
      credits: discipline.credits,
      description: discipline.description,
      program: discipline.program,
      presential: true,
    });
  }

  public listDisciplines(search?: Search) {
    const params: any = search;
    return this.http.get<Pagination<Discipline>>(this.base_url, { params });
  }

  public deleteDiscipline(discipline: Discipline) {
    const id = discipline.id?.value;
    return this.http.delete(this.base_url + '/' + id).pipe();
  }
}
