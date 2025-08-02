import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { ListarLibrosComponent } from './components/listar-libros/listar-libros.component';
import { CrearLibroComponent } from './components/crear-libro/crear-libro.component';
import { ListarUsuariosComponent } from './components/listar-usuarios/listar-usuarios.component';
import { CrearUsuarioComponent } from './components/crear-usuario/crear-usuario.component';
import { CrearReservaComponent } from './components/crear-reserva/crear-reserva.component';
import { VerReservasComponent } from './components/ver-reservas/ver-reservas.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ListarLibrosComponent,
    CrearLibroComponent,
    ListarUsuariosComponent,
    CrearUsuarioComponent,
    CrearReservaComponent,
    VerReservasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }