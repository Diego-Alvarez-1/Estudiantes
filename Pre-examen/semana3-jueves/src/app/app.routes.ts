import { Routes } from '@angular/router';
import { AulasComponent } from './aulas/aulas.component';
import { EstudiantesComponent } from './estudiantes/estudiantes.component';

export const routes: Routes = [
    {path:'aulas', component:AulasComponent},
    {path:'estudiantes', component:EstudiantesComponent}
];
