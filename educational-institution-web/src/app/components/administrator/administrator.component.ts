import { Component, ViewChild, OnInit } from '@angular/core';
import { Table } from 'primeng/table';
import { Search, User } from 'src/app/models';
import { UserService } from 'src/app/services/user.service';
import { Pagination } from '../../models/index';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css'],
})
export class AdministratorComponent {
  @ViewChild('dt')
  table!: Table;
  pagination: Pagination<User> = {
    perPage: 2,
  } as Pagination<User>;
  filterValue: string = '';
  isUpdate = false;
  selectedUser: User = {} as User;

  constructor(private userService: UserService) {}

  loadUsers(event?: any) {
    const filter: Search = {};

    if (event) {
      const first = event.first;
      const perPage = event.rows;
      const page = Math.floor(first / perPage);

      filter.page = page;
      filter.perPage = perPage;
    }

    if (this.filterValue.length > 0) filter.search = this.filterValue;

    this.userService.listUsers(filter).subscribe(
      (resp) => (this.pagination = resp),
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  createOrUpdateUserHandler() {
    if (this.isUpdate) {
      this.updateUser();
    } else {
      this.createUser();
    }
  }

  updateUser() {
    this.userService.updateUser(this.selectedUser).subscribe(
      (response) => {
        this.resetTable();
        console.log('Atualizado usuario com ID: %s', response.id);
      },
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  createUser() {
    this.userService.createUser(this.selectedUser).subscribe(
      (response) => {
        this.resetTable();
        console.log('Criado usuario com ID: %s', response.id);
      },
      (errorRespose) => console.log(errorRespose.error.message)
    );
  }

  createUserCancelHandler() {
    this.selectedUser = {} as User;
    this.isUpdate = false;
  }

  resetTable() {
    this.filterValue = '';
    this.table.reset();
    this.isUpdate = false;
    this.selectedUser = {} as User;
  }

  editUserHandler(user: User) {
    this.selectedUser = {
      id: user.id,
      name: user.name,
      profile: user.profile,
      username: user.username,
      password: user.password,
    };
    this.isUpdate = true;
  }

  deleteUserHandler(user: User) {
    this.userService.deleteUser(user).subscribe((resp) => {
      const page = {
        first: this.table.first,
        rows: this.table.rows,
      };
      this.loadUsers(page);
    });
  }
}
