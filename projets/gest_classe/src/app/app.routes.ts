import { Routes } from '@angular/router';

export const routes: Routes = [

    {
        path: "classe",
        loadComponent: () => import('./pages/classe/classe.component').then(c => c.ClasseComponent),
    },
    {
        path: "etudiant",
        loadComponent: () => import('./pages/etudiant/etudiant.component').then(c => c.EtudiantComponent)
    }
];
