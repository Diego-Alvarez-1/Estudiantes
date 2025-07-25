import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  constructor(private readonly http:HttpClient) { }
  
 api = 'http://localhost:8080/api/estudiantes';
  
  // CORRECCIÓN: era "guardarAlummnos" (doble m), ahora "guardarAlumnos"
  guardarAlumnos(body: any): Observable<any> {
    return this.http.post(this.api, body);
  }
  
  // CORRECCIÓN: renombrado para que tenga más sentido con estudiantes
  getLogin(): Observable<any> {
    return this.http.get(this.api);
  }
  
  // CORRECCIÓN: renombrado para que tenga más sentido
  deleteLogin(id: String): Observable<any> {
    return this.http.delete(this.api + '/' + id);
  }
  
  // CORRECCIÓN: el body debe ser un parámetro, no string literal
  updateLogin(id: string, body: any): Observable<any> {
    return this.http.put(this.api + '/' + id, body);
  }
  
  // MÉTODOS ADICIONALES para funcionalidades del backend
  cambiarEstado(id: number, habilitado: boolean): Observable<any> {
    const params = new HttpParams().set('habilitado', habilitado.toString());
    return this.http.put(`${this.api}/${id}/estado`, null, { params });
  }
  
  // Para aulas
  guardarAula(body: any): Observable<any> {
    return this.http.post(`${this.api}/aulas`, body);
  }
  
  listarAulas(): Observable<any> {
    return this.http.get(`${this.api}/aulas`);
  }
  
  asignarEstudianteAula(idAula: number, idEstudiante: number): Observable<any> {
    return this.http.post(`${this.api}/aulas/${idAula}/estudiantes/${idEstudiante}`, null);
  }
}