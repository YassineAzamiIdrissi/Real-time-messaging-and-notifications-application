import {Component} from '@angular/core';
import {AuthService} from "../../../services/services/auth.service";
import {RegistrationRequest} from "../../../services/models/registration-request";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  loading: boolean = false;
  registerRequest: RegistrationRequest = {
    email: "",
    password: "",
    firstName: "",
    lastName: ""
  }
  errors: Array<String> = [];
  simpErrorMessage: string = "";

  constructor(private authenticationService: AuthService,
              private router: Router) {
  }

  register() {
    this.loading = true
    this.simpErrorMessage = "";
    this.errors = [];
    this.authenticationService.register({
      body: this.registerRequest
    }).subscribe({
      next: (result) => {
        this.router.navigate(["activate-account"]);
        this.loading = false;
      },
      error: (err) => {
        console.log(err);
        if (err.error.errors) {
          this.errors = err.error.errors;
        } else {
          this.simpErrorMessage = err.error.message;
        }
        this.loading = false;
      }
    });
  }

  goToLogin() {
    this.router.navigate(["login"]);
  }
}
