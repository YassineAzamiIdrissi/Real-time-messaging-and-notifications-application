import {Component} from '@angular/core';
import {AuthService} from "../../../services/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-demand-password',
  templateUrl: './demand-password.component.html',
  styleUrl: './demand-password.component.scss'
})
export class DemandPasswordComponent {
  email: string = "";
  errorMessage: string = "";

  constructor(private authService: AuthService, private router: Router) {
  }

  checkEmail() {
    this.authService.demandRecovery({
      email: this.email
    }).subscribe(
      {
        next: (resp) => {
          this.router.navigate(["check-code", this.email]);
        },
        error: (err) => {
          this.errorMessage = err.error.message;
        }
      }
    )
  }
}
