import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { AppConfigurator } from './app.configurator';
import { LayoutService } from '../service/layout.service';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';

@Component({
    selector: 'app-topbar',
    standalone: true,
    imports: [RouterModule, CommonModule, StyleClassModule, AppConfigurator],
    template: ` <div class="layout-topbar">
        <div class="layout-topbar-logo-container">
            <button class="layout-menu-button layout-topbar-action" (click)="layoutService.onMenuToggle()">
                <i class="pi pi-bars"></i>
            </button>
            <a class="layout-topbar-logo" routerLink="/">
                <img src="assets/images/Logo-SONABEL.jpg" alt="" style="height: 60px;" />
            </a>
            <span style="font-size: 1.8rem;">
                <span style="color: #0b8c50; font-weight: bold;">GES</span>
                <span style="color: #ff0000; font-weight: bold;">CLI</span>
            </span>
        </div>

        <div class="layout-topbar-actions">
            <div class="layout-config-menu">
                <button type="button" class="layout-topbar-action" (click)="toggleDarkMode()">
                    <i [ngClass]="{ 'pi ': true, 'pi-moon': layoutService.isDarkTheme(), 'pi-sun': !layoutService.isDarkTheme() }"></i>
                </button>
                <div class="relative">
                    <button
                        class="layout-topbar-action layout-topbar-action-highlight"
                        pStyleClass="@next"
                        enterFromClass="hidden"
                        enterActiveClass="animate-scalein"
                        leaveToClass="hidden"
                        leaveActiveClass="animate-fadeout"
                        [hideOnOutsideClick]="true"
                    >
                        <i class="pi pi-palette"></i>
                    </button>
                    <app-configurator />
                </div>
            </div>

            <button class="layout-topbar-menu-button layout-topbar-action" pStyleClass="@next" enterFromClass="hidden" enterActiveClass="animate-scalein" leaveToClass="hidden" leaveActiveClass="animate-fadeout" [hideOnOutsideClick]="true">
                <i class="pi pi-ellipsis-v"></i>
            </button>

            <div class="layout-topbar-menu hidden lg:block">
                <div class="layout-topbar-menu-content">
                <button type="button" style="display: flex; flex-direction: column; align-items: center;" (click)="openAccount()">
  <i class="pi pi-user" style="font-size: 1.5rem;"></i>
  <span>{{ username }}</span>
</button>

                    <button type="button" class="layout-topbar-action">
                        <i class="pi pi-sign-out" (click)="logout()"></i>
                        <span>Déconnexion</span>
                    </button>
                </div>
            </div>
        </div>
    </div>`
})
export class AppTopbar {
  items!: MenuItem[];
  username: string = '';

  constructor(
    public layoutService: LayoutService,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit() {
    const user = this.keycloakService.getInstance().tokenParsed;
  const firstName = user?.['given_name'] || '';
  const lastName = user?.['family_name'] || '';
  this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';  }

  toggleDarkMode() {
    this.layoutService.layoutConfig.update((state) => ({
      ...state,
      darkTheme: !state.darkTheme,
    }));
  }

  logout(): void {
    this.keycloakService.logout();
  }

  openAccount() {
  this.keycloakService.openAccountManagement();
}


}
