import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../../services/services/auth.service";

@Component({
  selector: 'app-check-recovery',
  templateUrl: './check-recovery.component.html',
  styleUrl: './check-recovery.component.scss'
})
export class CheckRecoveryComponent implements OnInit {
  email: string = "";
  message: string = "";
  submitted: boolean = false;
  witness: boolean = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.email = this.activatedRoute.snapshot.params['email'];
  }

  checkCodeValidity(code: string) {
    this.message = "";
    this.submitted = true;
    this.authService.checkRecovery({
      recovery: code
    }).subscribe({
      next: (resp) => {
        this.router.navigate(["set-new-password"],
          {
            queryParams: {
              recovery: code
            }
          });
      },
      error: (err) => {
        console.log(err);
        this.message = err.error.message;
        this.witness = true;
      }
    })
  }
}
