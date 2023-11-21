import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  constructor(private router: Router) {}

  navigateToAdministrator() {
    this.router.navigate(['/administrator']);
  }

  navigateToCoordinator() {
    this.router.navigate(['/coordinator']);
  }

  navigateToTeacher() {
    this.router.navigate(['/teacher']);
  }

  navigateToStudent() {
    this.router.navigate(['/student']);
  }
}
