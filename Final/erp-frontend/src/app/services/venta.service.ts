import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseWrapper, Venta, EstadoVenta } from '../models/response-wrapper.model';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private baseUrl = 'http://localhost:8080/api/ventas';

  constructor(private http: HttpClient) { }

  crear(venta: any): Observable<ResponseWrapper<Venta>> {
    return this.http.post<ResponseWrapper<Venta>>(this.baseUrl, venta);
  }

  listar(): Observable<ResponseWrapper<Venta[]>> {
    return this.http.get<ResponseWrapper<Venta[]>>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<ResponseWrapper<Venta>> {
    return this.http.get<ResponseWrapper<Venta>>(`${this.baseUrl}/${id}`);
  }

  obtenerPorCliente(clienteId: number): Observable<ResponseWrapper<Venta[]>> {
    return this.http.get<ResponseWrapper<Venta[]>>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  obtenerPorEstado(estado: EstadoVenta): Observable<ResponseWrapper<Venta[]>> {
    return this.http.get<ResponseWrapper<Venta[]>>(`${this.baseUrl}/estado/${estado}`);
  }

  cambiarEstado(id: number, estado: EstadoVenta): Observable<ResponseWrapper<Venta>> {
    return this.http.put<ResponseWrapper<Venta>>(`${this.baseUrl}/${id}/estado?estado=${estado}`, {});
  }

  cancelar(id: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(`${this.baseUrl}/${id}`);
  }
}