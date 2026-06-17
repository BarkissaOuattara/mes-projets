import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Dashboard } from './app/pages/dashboard/dashboard';
import { Documentation } from './app/pages/documentation/documentation';
import { Landing } from './app/pages/landing/landing';
import { Notfound } from './app/pages/notfound/notfound';
import { BranchementComponent } from './app/share/branchement/branchement.component';
import { AppComponent } from './app.component';
import { AbonnementComponent } from './app/pages/abonnement/abonnement.component';
import { ExploitationComponent } from './app/share/exploitation/exploitation.component';
import { TypeBranchComponent } from './app/share/typebranch/typebranch.component';
import { LogComponent } from './app/share/log/log.component';
import { ChartLineStyle } from './app/pages/dashboard/components/chartlinestyle';

export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            { path: '', component: Dashboard },
            { path: 'uikit', loadChildren: () => import('./app/pages/uikit/uikit.routes') },
            { path: 'documentation', component: Documentation },
            { path: 'pages', loadChildren: () => import('./app/pages/pages.routes') },
            {
                path: "branchement",
                component: BranchementComponent
            },

            {
                path: "abonnement",
                component: AbonnementComponent
            },
            
              { path: 'log',
                 component: LogComponent },

            {
                path: "exploitation",
                component: ExploitationComponent
            },

             {
                path: "typebranch",
                component: TypeBranchComponent
            },

             { path: 'statistique',
                 component: ChartLineStyle

             },
            {
                path: "typecli",
                loadComponent: () => import('./app/share/typecli/typeclient.component').then(c => c.TypeClientComponent)
            },
        
            {
                path: "tarif",
                loadComponent: () => import('./app/share/tarif/tarif.component').then(c => c.TarifComponent)
            },
        
            {
                path: "abonne",
                loadComponent: () => import('./app/share/abonne/abonne.component').then(c => c.AbonneComponent)
            }
        

        ]
    },
   { path: 'landing', component: Landing },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' },
    
];
