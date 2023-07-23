import { Injectable, inject } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './services/user.service';
import { interceptIgnore } from './endpoint.constants';

@Injectable()
export class HttpJWTInterceptor implements HttpInterceptor {
  constructor() {}
  userSvc = inject(UserService);

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.checkIgnore(req.url)) return next.handle(req); //TODO: is there a better way?
    let authReq = req;
    if (this.userSvc.accessToken != null) {
      authReq = req.clone({
        headers: req.headers.set(
          'Authorization',
          'Bearer ' + this.userSvc.accessToken
        ),
      });
    }
    return next.handle(authReq);
  }

  checkIgnore(url: string) {
    for(let i = 0; i < interceptIgnore.length; i++) {
        if (url.includes(interceptIgnore[i])) return true ;
    }
    return false;
  }
}

export const httpInterceptorProvider = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpJWTInterceptor, multi: true },
];
