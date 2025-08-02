import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarLibrosComponent } from './components/listar-libros/listar-libros.component';
import { CrearLibroComponent } from './components/crear-libro/crear-libro.component';
import { ListarUsuariosComponent } from './components/listar-usuarios/listar-usuarios.component';
import { CrearUsuarioComponent } from './components/crear-usuario/crear-usuario.component';
import { CrearReservaComponent } from './components/crear-reserva/crear-reserva.component';
import { VerReservasComponent } from './components/ver-reservas/ver-reservas.component';

const routes: Routes = [
  { path: '', redirectTo: '/libros', pathMatch: 'full' },
  { path: 'libros', component: ListarLibrosComponent },
  { path: 'crear-libro', component: CrearLibroComponent },
  { path: 'usuarios', component: ListarUsuariosComponent },
  { path: 'crear-usuario', component: CrearUsuarioComponent },
  { path: 'crear-reserva', component: CrearReservaComponent },
  { path: 'ver-reservas', component: VerReservasComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }