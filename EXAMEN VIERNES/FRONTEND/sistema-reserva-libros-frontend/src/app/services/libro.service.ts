import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { Libro, LibroDTO } from '../models/libro.model';

@Injectable({
  providedIn: 'root'
})
export class LibroService {
  private readonly url = 'http://localhost:8080/api/libros';

  constructor(private readonly http: HttpClient) { }

  listarLibros(): Observable<ApiResponse<Libro[]>> {
    return this.http.get<ApiResponse<Libro[]>>(this.url);
  }

  listarLibrosDisponibles(): Observable<ApiResponse<Libro[]>> {
    return this.http.get<ApiResponse<Libro[]>>(`${this.url}/disponibles`);
  }

  crearLibro(libro: LibroDTO): Observable<ApiResponse<Libro>> {
    return this.http.post<ApiResponse<Libro>>(this.url, libro);
  }
}