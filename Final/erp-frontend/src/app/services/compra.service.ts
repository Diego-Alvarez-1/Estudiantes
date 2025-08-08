import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseWrapper, Compra, EstadoCompra } from '../models/response-wrapper.model';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseUrl = 'http://localhost:8080/api/compras';

  constructor(private http: HttpClient) { }

  crear(compra: any): Observable<ResponseWrapper<Compra>> {
    return this.http.post<ResponseWrapper<Compra>>(this.baseUrl, compra);
  }

  listar(): Observable<ResponseWrapper<Compra[]>> {
    return this.http.get<ResponseWrapper<Compra[]>>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<ResponseWrapper<Compra>> {
    return this.http.get<ResponseWrapper<Compra>>(`${this.baseUrl}/${id}`);
  }

  obtenerPorProveedor(proveedorId: number): Observable<ResponseWrapper<Compra[]>> {
    return this.http.get<ResponseWrapper<Compra[]>>(`${this.baseUrl}/proveedor/${proveedorId}`);
  }

  obtenerPorEstado(estado: EstadoCompra): Observable<ResponseWrapper<Compra[]>> {
    return this.http.get<ResponseWrapper<Compra[]>>(`${this.baseUrl}/estado/${estado}`);
  }

  cambiarEstado(id: number, estado: EstadoCompra): Observable<ResponseWrapper<Compra>> {
    return this.http.put<ResponseWrapper<Compra>>(`${this.baseUrl}/${id}/estado?estado=${estado}`, {});
  }

  cancelar(id: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(`${this.baseUrl}/${id}`);
  }
}