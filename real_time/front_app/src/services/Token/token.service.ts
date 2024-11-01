import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor() {
  }

  set token(token: string) {
    localStorage.setItem("token", token);
  }

  get token() {
    return localStorage.getItem("token") as string;
  }


  isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    const jwtHelper = new JwtHelperService();
    if (jwtHelper.isTokenExpired(token)) {
      localStorage.removeItem("token");
      return false;
    }
    return true;
  }

  parseTokenData() {
    if (!this.token) {
      return null;
    }
    const payload = this.token.split(".")[1];
    return JSON.parse(atob(payload));
  }
}
