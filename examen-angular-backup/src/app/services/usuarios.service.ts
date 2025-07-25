import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  private usuariosSubject = new BehaviorSubject<any[]>([]);
  public usuarios$ = this.usuariosSubject.asObservable();

  constructor(private http: HttpClient) {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.http.get<any[]>('/assets/data/usuarios.json').subscribe(usuarios => {
      this.usuariosSubject.next(usuarios);
    });
  }

  obtenerUsuarios(): Observable<any[]> {
    return this.usuarios$;
  }

  agregarUsuario(usuario: any) {
    const usuarios = this.usuariosSubject.value;
    const nuevoId = Math.max(...usuarios.map(u => u.id)) + 1;
    const nuevoUsuario = { ...usuario, id: nuevoId };
    this.usuariosSubject.next([...usuarios, nuevoUsuario]);
  }
}