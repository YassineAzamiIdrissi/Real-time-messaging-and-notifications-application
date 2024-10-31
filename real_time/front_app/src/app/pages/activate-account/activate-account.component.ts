import {Component} from '@angular/core';
import {AuthService} from "../../../services/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
  submitted: boolean = false;
  codeCorrect: boolean = false;
  message: string = "";

  constructor(private authService: AuthService,
              private router: Router) {
  }

  checkCode(code: string) {
    this.submitted = true;
    this.authService.activateAccount({
      code
    }).subscribe({
      next: (resp) => {
        this.codeCorrect = true;
      },
      error: (error) => {
        this.codeCorrect = false;
        this.message = error.error.message;
        console.log(error);
      }
    })
  }

  login() {
    this.router.navigate(["login"]);
  }
}
