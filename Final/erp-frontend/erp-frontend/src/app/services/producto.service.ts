import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseWrapper, Producto } from '../models/response-wrapper.model';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private baseUrl = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) { }

  crear(producto: Producto): Observable<ResponseWrapper<Producto>> {
    return this.http.post<ResponseWrapper<Producto>>(this.baseUrl, producto);
  }

  listar(): Observable<ResponseWrapper<Producto[]>> {
    return this.http.get<ResponseWrapper<Producto[]>>(this.baseUrl);
  }

  obtenerPorId(id: number): Observable<ResponseWrapper<Producto>> {
    return this.http.get<ResponseWrapper<Producto>>(`${this.baseUrl}/${id}`);
  }

  actualizar(id: number, producto: Producto): Observable<ResponseWrapper<Producto>> {
    return this.http.put<ResponseWrapper<Producto>>(`${this.baseUrl}/${id}`, producto);
  }

  desactivar(id: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(`${this.baseUrl}/${id}`);
  }

  obtenerStockBajo(): Observable<ResponseWrapper<Producto[]>> {
    return this.http.get<ResponseWrapper<Producto[]>>(`${this.baseUrl}/stock-bajo`);
  }

  actualizarStock(id: number, cantidad: number, operacion: string): Observable<ResponseWrapper<void>> {
    return this.http.put<ResponseWrapper<void>>(`${this.baseUrl}/${id}/stock?cantidad=${cantidad}&operacion=${operacion}`, {});
  }
}