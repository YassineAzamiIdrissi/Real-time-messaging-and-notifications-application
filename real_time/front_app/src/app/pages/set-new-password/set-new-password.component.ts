import {Component, OnInit} from '@angular/core';
import {PasswordsCoupleRequest} from "../../../services/models/passwords-couple-request";
import {AuthService} from "../../../services/services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-set-new-password',
  templateUrl: './set-new-password.component.html',
  styleUrl: './set-new-password.component.scss'
})
export class SetNewPasswordComponent implements OnInit {
  recovery: string = "";
  passwordsCouple: PasswordsCoupleRequest = {
    confirmNewPassword: "",
    newPassword: ""
  }
  message: string = "";
  witness: boolean = false;
  errorMessages: Array<String> = [];

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.recovery = this.activatedRoute.snapshot.queryParams['recovery'];
    console.log(this.recovery);
  }

  setNewPassword() {
    this.message = "";
    this.errorMessages = [];
    this.authService.setNewPassword({
        recovery: this.recovery,
        body: this.passwordsCouple
      }
    ).subscribe({
      next: (resp) => {
        this.message = "Password reset correctly... you can ";
        this.witness = true;
      },
      error: (err) => {
        console.log(err);
        if (err.error.errors) {
          this.errorMessages = err.error.errors;
        } else {
          this.message = err.error.message;
        }
        this.witness = false;
      }
    })
  }

  goToLogin() {
    this.router.navigate(["login"]);
  }
}
