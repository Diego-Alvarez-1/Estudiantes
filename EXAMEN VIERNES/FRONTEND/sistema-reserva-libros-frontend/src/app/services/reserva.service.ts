import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { Reserva, ReservaDTO } from '../models/reserva.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private readonly url = 'http://localhost:8080/api/reservas';

  constructor(private readonly http: HttpClient) { }

  crearReserva(reserva: ReservaDTO): Observable<ApiResponse<Reserva>> {
    return this.http.post<ApiResponse<Reserva>>(this.url, reserva);
  }

  obtenerReservasPorUsuario(usuarioId: number): Observable<ApiResponse<Reserva[]>> {
    return this.http.get<ApiResponse<Reserva[]>>(`${this.url}/usuario/${usuarioId}`);
  }
}