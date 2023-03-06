import {Injectable} from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse
} from '@angular/common/http';

import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {AuthService} from './auth.service';
import {tap} from 'rxjs/operators';

/** Inject With Credentials into the request */
@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    console.log('interceptor: ' + request.url);

    // This is required if we set cors() in Spring Security.
    // In such a case Chrome won't let us in.
    request = request.clone({
      withCredentials: true
    });

    return next.handle(request).pipe(tap({
      next: () => {
      },
      error: (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.authService.clear();
          console.log("some login error occurred")
          this.router.navigate(['login']);
        }
      }
    }));
  }
}
