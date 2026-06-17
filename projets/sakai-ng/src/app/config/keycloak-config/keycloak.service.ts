import { Injectable } from '@angular/core';
import Keycloak, { KeycloakProfile } from 'keycloak-js';
import { environment } from '../../../environments/environment';
import { SERVER_URL } from '../proxy-config';

@Injectable({
    providedIn: 'root'
})
export class KeycloakService {

    private static KEYCLOAK_SINGLETON: Keycloak;

    constructor() {
    }

    async init(callback: Function) {
        KeycloakService.KEYCLOAK_SINGLETON = new Keycloak({
            url: `${SERVER_URL.GESCLI_KEYCLOAK_PORT}`,
            realm: 'Sonabel',
            clientId: 'gestion-abonnement'
        });

        try {
            const authenticated = await KeycloakService.KEYCLOAK_SINGLETON.init({
                checkLoginIframe: false,
                onLoad: 'login-required'
            });
            if (authenticated) {
                callback();
            } else {
                console.log('User is not authenticated');

            }
        } catch (error) {
            console.error('Failed to initialize adapter:', error);
        }
    }

    logout(redirectUri?: string): void {
        console.log(KeycloakService.KEYCLOAK_SINGLETON);
        KeycloakService.KEYCLOAK_SINGLETON?.logout({
            redirectUri: redirectUri || window.location.origin
        });
    }

    getInstance(){
        return KeycloakService.KEYCLOAK_SINGLETON;
    }


    getToken() {
        return KeycloakService.KEYCLOAK_SINGLETON.token
    }

    getProfile():Promise<KeycloakProfile>{
        console.log(KeycloakService.KEYCLOAK_SINGLETON.profile);
        return KeycloakService.KEYCLOAK_SINGLETON.loadUserProfile()
    }

    openAccountManagement(): void {
    if (KeycloakService.KEYCLOAK_SINGLETON) {
        KeycloakService.KEYCLOAK_SINGLETON.accountManagement()
            .then(() => console.log('Redirection vers la gestion du compte...'))
            .catch((err) => console.error('Erreur lors de la redirection :', err));
    } else {
        console.warn('Keycloak non initialisé');
    }
}

}
