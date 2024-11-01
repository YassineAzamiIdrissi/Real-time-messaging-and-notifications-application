import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenService} from "../services/Token/token.service";

export const authGuard: CanActivateFn =
  (route, state) => {
    const tokenService = inject(TokenService);
    const router = inject(Router);
    if (!tokenService.isTokenValid()) {
      localStorage.removeItem("token");
      router.navigate(["login"]);
      return false;
    }
    return true;
  };
