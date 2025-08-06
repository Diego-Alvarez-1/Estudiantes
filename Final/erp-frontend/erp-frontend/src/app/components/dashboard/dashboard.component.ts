import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../services/cliente.service';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalClientes = 0;
  totalProductos = 0;
  totalVentas = 0;
  totalProveedores = 0;
  
  productosStockBajo: Producto[] = [];
  
  ventasDelMes = 0;
  comprasDelMes = 0;
  ventasPendientes = 0;
  comprasPendientes = 0;

  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService
  ) { }

  ngOnInit(): void {
    this.cargarEstadisticas();
  }

  cargarEstadisticas(): void {
    // Cargar clientes
    this.clienteService.listar().subscribe({
      next: (response) => {
        this.totalClientes = response.data?.length || 0;
      },
      error: (error) => console.error('Error al cargar clientes:', error)
    });

    // Cargar productos
    this.productoService.listar().subscribe({
      next: (response) => {
        this.totalProductos = response.data?.length || 0;
      },
      error: (error) => console.error('Error al cargar productos:', error)
    });

    // Cargar productos con stock bajo
    this.productoService.obtenerStockBajo().subscribe({
      next: (response) => {
        this.productosStockBajo = response.data || [];
      },
      error: (error) => console.error('Error al cargar productos stock bajo:', error)
    });

    // Simulación de datos para el ejemplo
    // En una implementación real, estos datos vendrían de servicios
    this.totalVentas = 25;
    this.totalProveedores = 12;
    this.ventasDelMes = 45000;
    this.comprasDelMes = 32000;
    this.ventasPendientes = 3;
    this.comprasPendientes = 2;
  }
}