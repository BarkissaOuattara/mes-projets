import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app.config';
import { AppComponent } from './app.component';
import { KeycloakService } from './app/config/keycloak-config/keycloak.service';

let keycloakService = new KeycloakService();

keycloakService.init(
    ()=>{
        bootstrapApplication(AppComponent, appConfig)
            .catch((err) => console.error(err));
    }
);