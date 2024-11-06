import {Component} from '@angular/core';
import {AuthenticationRequest} from "../../../services/models/authentication-request";
import {AuthService} from "../../../services/services/auth.service";
import {Router} from "@angular/router";
import {TokenService} from "../../../services/Token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authReq: AuthenticationRequest = {
    email: "",
    password: ""
  }
  errorMessages: Array<String> = [];
  simpErrorMessage: string = "";

  constructor(private authService: AuthService,
              private router: Router,
              private tokenService: TokenService) {
  }

  login() {
    console.log(this.authReq);
    this.errorMessages = [];
    this.simpErrorMessage = "";
    this.authService.authenticate(
      {
        body: this.authReq
      }
    ).subscribe(
      {
        next: (resp) => {
          this.tokenService.token = resp.token as string;
          this.router.navigate(["users"]);
        },
        error: (err) => {
          if (err.error.errors) {
            this.errorMessages = err.error.errors;
          } else {
            this.simpErrorMessage = err.error.message;
          }
          console.log(err);
        }
      }
    )
  }

  goToRegister() {
    this.router.navigate(["register"]);
  }

  goToDemandRecovery() {
    this.router.navigate(["demand-recovery"]);
  }
}
