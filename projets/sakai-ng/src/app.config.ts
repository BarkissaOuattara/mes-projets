import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest, provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, inject } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';
import { Observable } from 'rxjs';
import { KeycloakService } from './app/config/keycloak-config/keycloak.service';
import { Component } from '@angular/core';





export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(appRoutes, withInMemoryScrolling({ anchorScrolling: 'enabled', scrollPositionRestoration: 'enabled' }), withEnabledBlockingInitialNavigation()),
        provideHttpClient(withFetch(), withInterceptors([TokenInterceptor])),
        provideAnimationsAsync(),
        providePrimeNG({ theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } } })
    ]
};

function TokenInterceptor(
    req: HttpRequest<unknown>,
    next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {

    const keycloakService = inject(KeycloakService);
    const token = keycloakService.getToken();

    if(token){
        req = req.clone({
            headers: new HttpHeaders({
                "Authorization": `Bearer ${token}`
            })
        });
    }
    return next(req);
}


export class AppConfigurator {}