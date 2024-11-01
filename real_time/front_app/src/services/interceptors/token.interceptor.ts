import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from "@angular/core";
import {TokenService} from "../Token/token.service";

export const tokenInterceptor: HttpInterceptorFn =
  (req, next) => {
    const tokenService = inject(TokenService);
    const token = tokenService.token;
    if (token) {
      const clonedRequest = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
      return next(clonedRequest);
    }
    return next(req);
  };
