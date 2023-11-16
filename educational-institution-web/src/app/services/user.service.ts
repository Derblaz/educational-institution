import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Pagination, Search, User } from '../models';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private base_url = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  public createUser(user: User) {
    const { name, username, profile, password } = user;
    return this.http
      .post<{ id: string }>(this.base_url, {
        name,
        username,
        profile,
        password,
      })
      .pipe();
  }

  public updateUser(user: User) {
    const id = user.id?.value;
    const body = {
      name: user.name,
      username: user.username,
      profile: user.profile,
      password: user.password,
    };
    return this.http.put<{ id: string }>(this.base_url + '/' + id, body);
  }

  public getUser(user: User) {
    const id = user.id?.value;
    return this.http.get<User>(this.base_url + '/' + id);
  }

  public listUsers(search?: Search) {
    const params: any = search;
    return this.http.get<Pagination<User>>(this.base_url, { params });
  }

  public deleteUser(user: User) {
    const id = user.id?.value;
    return this.http.delete(this.base_url + '/' + id);
  }
}
