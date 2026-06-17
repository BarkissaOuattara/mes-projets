import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AppMenuitem } from './app.menuitem';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { filter } from 'rxjs';
import { Access } from '../../pages/auth/access';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `<ul class="layout-menu">
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> `
})
export class AppMenu  {
    model: MenuItem[] = [];
    

    constructor(private keycloakService: KeycloakService){
        
    }

    ngOnInit() {
    const rawMenu: any[] = [
        {
            label: 'Accueil',
            permission: ['admin', 'guich', 'tech'],
            items: [
                { label: 'Dashboard', permission: ['admin', 'guich', 'tech'], icon: 'pi pi-fw pi-home', routerLink: ['/'] },
                { label: 'Branchement', permission: ['admin', 'guich', 'tech'], icon: 'pi pi-bolt text-xl ', routerLink: ['/branchement'] },
                { label: 'Abonnés', permission: ['admin', 'guich'], icon: 'pi pi-users text-xl ', routerLink: ['/abonne'] },
                { label: 'Abonnement', permission: ['admin', 'guich'], icon: 'pi pi-file-edit text-xl ', routerLink: ['/abonnement'] },
            ]
        },
        {
            label: 'Parametrage',
            permission: ['admin'],
            items: [
                { label: 'Tarif', permission: ['admin'], icon: 'pi pi-money-bill text-xl ', routerLink: ['/tarif'] },
                { label: 'Type client', permission: ['admin'], icon: 'pi pi-tag text-xl ', routerLink: ['/typecli'] },
                { label: 'Exploitation', permission: ['admin'], icon: 'pi pi-building text-xl ', routerLink: ['/exploitation'] },
                { label: 'Type Branchement', permission: ['admin'], icon: 'pi pi-bolt text-xl ', routerLink: ['/typebranch'] },
                { label: 'Log', permission: ['admin'], icon: 'pi pi-bolt text-xl ', routerLink: ['/log'] },
            ]
        }
    ];

    this.model = rawMenu
        .map(menu => {
            const filteredItems = menu.items?.filter((item: any) =>
                this.filterMenu(item)
            );
            return {
                ...menu,
                items: filteredItems
            };
        })
        .filter(menu => menu.items && menu.items.length > 0 && this.filterMenu(menu));
}

filterMenu(menu: any): boolean {
    if (Array.isArray(menu.permission)) {
        return menu.permission.some((role: string) =>
            this.keycloakService.getInstance().hasRealmRole(role)
        );
    }
    return false;
}
}