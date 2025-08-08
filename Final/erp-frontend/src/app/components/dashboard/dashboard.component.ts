import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../services/cliente.service';
import { ProductoService } from '../../services/producto.service';
import { VentaService } from '../../services/venta.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  totalClientes = 0;
  totalProductos = 0;
  ventasMes = 0;
  productosStockBajo = 0;

  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private ventaService: VentaService
  ) { }

  ngOnInit(): void {
    this.cargarEstadisticas();
  }

  cargarEstadisticas(): void {
    // Cargar total de clientes
    this.clienteService.listar().subscribe({
      next: (response) => {
        this.totalClientes = response.data.length;
      },
      error: (error) => console.error('Error cargando clientes:', error)
    });

    // Cargar total de productos
    this.productoService.listar().subscribe({
      next: (response) => {
        this.totalProductos = response.data.length;
      },
      error: (error) => console.error('Error cargando productos:', error)
    });

    // Cargar productos con stock bajo
    this.productoService.obtenerStockBajo().subscribe({
      next: (response) => {
        this.productosStockBajo = response.data.length;
      },
      error: (error) => console.error('Error cargando stock bajo:', error)
    });

    // Cargar ventas del mes
    this.ventaService.listar().subscribe({
      next: (response) => {
        this.ventasMes = response.data.length;
      },
      error: (error) => console.error('Error cargando ventas:', error)
    });
  }
}