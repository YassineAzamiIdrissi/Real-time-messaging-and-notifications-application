import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../../../services/Token/token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  username: string = "";

  constructor(private tokenService: TokenService,
              private router: Router) {
  }

  ngOnInit() {
    const payload = this.tokenService.parseTokenData();
    console.log(payload);
    this.username = payload.fullName;
  }

  logout() {
    localStorage.removeItem("token");
    this.router.navigate(["login"]);
  }
}
