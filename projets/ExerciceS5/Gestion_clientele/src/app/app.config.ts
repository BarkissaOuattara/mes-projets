import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { provideRouter } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { Observable } from 'rxjs';



import { routes } from './app.routes';
import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest, provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([AppInterceptor]),

    ),
    provideAnimationsAsync(),
    providePrimeNG({
        theme: {
            preset: Aura,
            options: {
              darkModeSelector: '.dark'
            }
          }
    })
  ]
};

function AppInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {
  req = req.clone({
    headers: new HttpHeaders({
      "Access-Control-Allow-Origin": "*"
      //"Authorization": `Bearer ${token}`
    })
  });
  return next(req);
}
