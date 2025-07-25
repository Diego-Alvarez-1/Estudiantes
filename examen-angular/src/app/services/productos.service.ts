import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  private productosSubject = new BehaviorSubject<any[]>([]);
  public productos$ = this.productosSubject.asObservable();

  constructor(private http: HttpClient) {
    this.cargarProductos();
  }

  cargarProductos() {
    this.http.get<any[]>('/assets/data/productos.json').subscribe(productos => {
      this.productosSubject.next(productos);
    });
  }

  obtenerProductos(): Observable<any[]> {
    return this.productos$;
  }

  agregarProducto(producto: any) {
    const productos = this.productosSubject.value;
    const nuevoId = Math.max(...productos.map(p => p.id)) + 1;
    const nuevoProducto = { ...producto, id: nuevoId };
    this.productosSubject.next([...productos, nuevoProducto]);
  }
}