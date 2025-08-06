import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseWrapper, Cliente } from '../models/response-wrapper.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private baseUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) { }

  crear(cliente: Cliente): Observable<ResponseWrapper<Cliente>> {
    return this.http.post<ResponseWrapper<Cliente>>(this.baseUrl, cliente);
  }

  listar(): Observable<ResponseWrapper<Cliente[]>> {
    return this.http.get<ResponseWrapper<Cliente[]>>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<ResponseWrapper<Cliente>> {
    return this.http.get<ResponseWrapper<Cliente>>(`${this.baseUrl}/${id}`);
  }

  actualizar(id: number, cliente: Cliente): Observable<ResponseWrapper<Cliente>> {
    return this.http.put<ResponseWrapper<Cliente>>(`${this.baseUrl}/${id}`, cliente);
  }

  desactivar(id: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(`${this.baseUrl}/${id}`);
  }

  activar(id: number): Observable<ResponseWrapper<void>> {
    return this.http.put<ResponseWrapper<void>>(`${this.baseUrl}/${id}/activar`, {});
  }

  buscarPorNombre(nombre: string): Observable<ResponseWrapper<Cliente[]>> {
    return this.http.get<ResponseWrapper<Cliente[]>>(`${this.baseUrl}/buscar?nombre=${nombre}`);
  }
}