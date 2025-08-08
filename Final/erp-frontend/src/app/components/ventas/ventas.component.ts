import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VentaService } from '../../services/venta.service';
import { ClienteService } from '../../services/cliente.service';
import { ProductoService } from '../../services/producto.service';
import { Venta, Cliente, Producto, DetalleVenta, EstadoVenta } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.css']
})
export class VentasComponent implements OnInit {
  
  ventas: Venta[] = [];
  clientes: Cliente[] = [];
  productos: Producto[] = [];
  detallesVenta: DetalleVenta[] = [];
  
  mostrarModal = false;
  mostrarModalDetalle = false;
  ventaSeleccionada: Venta | null = null;
  productoSeleccionado: Producto | null = null;
  cantidadProducto: number = 1;
  
  ventaForm: FormGroup;

  constructor(
    private ventaService: VentaService,
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private fb: FormBuilder
  ) {
    this.ventaForm = this.fb.group({
      clienteId: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.cargarVentas();
    this.cargarClientes();
    this.cargarProductos();
  }

  cargarVentas(): void {
    this.ventaService.listar().subscribe({
      next: (response) => {
        this.ventas = response.data;
      },
      error: (error) => console.error('Error cargando ventas:', error)
    });
  }

  cargarClientes(): void {
    this.clienteService.listar().subscribe({
      next: (response) => {
        this.clientes = response.data;
      },
      error: (error) => console.error('Error cargando clientes:', error)
    });
  }

  cargarProductos(): void {
    this.productoService.listar().subscribe({
      next: (response) => {
        this.productos = response.data;
      },
      error: (error) => console.error('Error cargando productos:', error)
    });
  }

  abrirModalCrear(): void {
    this.detallesVenta = [];
    this.productoSeleccionado = null;
    this.cantidadProducto = 1;
    this.ventaForm.reset();
    this.mostrarModal = true;
  }

  agregarDetalle(): void {
    if (this.productoSeleccionado && this.cantidadProducto > 0) {
      const subtotal = this.productoSeleccionado.precio * this.cantidadProducto;
      
      const detalle: DetalleVenta = {
        producto: this.productoSeleccionado,
        cantidad: this.cantidadProducto,
        precioUnitario: this.productoSeleccionado.precio,
        subtotal: subtotal
      };
      
      this.detallesVenta.push(detalle);
      this.productoSeleccionado = null;
      this.cantidadProducto = 1;
    }
  }

  removerDetalle(index: number): void {
    this.detallesVenta.splice(index, 1);
  }

  calcularTotal(): number {
    return this.detallesVenta.reduce((total, detalle) => total + detalle.subtotal!, 0);
  }

  guardarVenta(): void {
    if (this.ventaForm.valid && this.detallesVenta.length > 0) {
      const ventaData = {
        clienteId: this.ventaForm.value.clienteId,
        detalles: this.detallesVenta.map(detalle => ({
          productoId: detalle.producto?.id,
          cantidad: detalle.cantidad
        }))
      };
      
      this.ventaService.crear(ventaData).subscribe({
        next: () => {
          this.cargarVentas();
          this.cerrarModal();
        },
        error: (error) => console.error('Error creando venta:', error)
      });
    }
  }

  verDetalle(venta: Venta): void {
    this.ventaSeleccionada = venta;
    this.mostrarModalDetalle = true;
  }

  cambiarEstado(venta: Venta): void {
    this.ventaService.cambiarEstado(venta.id!, EstadoVenta.PAGADA).subscribe({
      next: () => this.cargarVentas(),
      error: (error) => console.error('Error cambiando estado:', error)
    });
  }

  cancelarVenta(venta: Venta): void {
    if (confirm('¿Está seguro de cancelar esta venta?')) {
      this.ventaService.cancelar(venta.id!).subscribe({
        next: () => this.cargarVentas(),
        error: (error) => console.error('Error cancelando venta:', error)
      });
    }
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'warning';
      case 'PAGADA': return 'active';
      case 'CANCELADA': return 'inactive';
      default: return '';
    }
  }

  cerrarModal(event?: any): void {
    if (event && event.target === event.currentTarget) {
      this.mostrarModal = false;
    } else if (!event) {
      this.mostrarModal = false;
    }
  }

  cerrarModalDetalle(event?: any): void {
    if (event && event.target === event.currentTarget) {
      this.mostrarModalDetalle = false;
    } else if (!event) {
      this.mostrarModalDetalle = false;
    }
  }
}