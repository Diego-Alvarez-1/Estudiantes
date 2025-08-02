import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { Usuario, UsuarioDTO } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private readonly url = 'http://localhost:8080/api/usuarios';

  constructor(private readonly http: HttpClient) { }

  listarUsuarios(): Observable<ApiResponse<Usuario[]>> {
    return this.http.get<ApiResponse<Usuario[]>>(this.url);
  }

  crearUsuario(usuario: UsuarioDTO): Observable<ApiResponse<Usuario>> {
    return this.http.post<ApiResponse<Usuario>>(this.url, usuario);
  }
}