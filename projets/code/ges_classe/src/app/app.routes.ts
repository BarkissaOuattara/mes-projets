import { Routes } from '@angular/router';
import { ClasseComponent } from './pages/classe/classe.component';

export const routes: Routes = [
    //{
    //    path: "classe",
    //    component: ClasseComponent
    //},
    {
        path: "",
        loadComponent: () => import('./pages/classe/classe.component').then(c => c.ClasseComponent),
    }
];
