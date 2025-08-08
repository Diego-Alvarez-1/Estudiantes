import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CompraService } from '../../services/compra.service';
import { ProveedorService } from '../../services/proveedor.service';
import { ProductoService } from '../../services/producto.service';
import { Compra, Proveedor, Producto, DetalleCompra, EstadoCompra } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.css']
})
export class ComprasComponent implements OnInit {
  
  compras: Compra[] = [];
  proveedores: Proveedor[] = [];
  productos: Producto[] = [];
  detallesCompra: DetalleCompra[] = [];
  
  mostrarModal = false;
  mostrarModalDetalle = false;
  compraSeleccionada: Compra | null = null;
  productoSeleccionado: Producto | null = null;
  cantidadProducto: number = 1;
  precioUnitario: number = 0;
  
  compraForm: FormGroup;

  constructor(
    private compraService: CompraService,
    private proveedorService: ProveedorService,
    private productoService: ProductoService,
    private fb: FormBuilder
  ) {
    this.compraForm = this.fb.group({
      proveedorId: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.cargarCompras();
    this.cargarProveedores();
    this.cargarProductos();
  }

  cargarCompras(): void {
    this.compraService.listar().subscribe({
      next: (response) => {
        this.compras = response.data;
      },
      error: (error) => console.error('Error cargando compras:', error)
    });
  }

  cargarProveedores(): void {
    this.proveedorService.listar().subscribe({
      next: (response) => {
        this.proveedores = response.data;
      },
      error: (error) => console.error('Error cargando proveedores:', error)
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
    this.detallesCompra = [];
    this.productoSeleccionado = null;
    this.cantidadProducto = 1;
    this.precioUnitario = 0;
    this.compraForm.reset();
    this.mostrarModal = true;
  }

  agregarDetalle(): void {
    if (this.productoSeleccionado && this.cantidadProducto > 0 && this.precioUnitario > 0) {
      const subtotal = this.precioUnitario * this.cantidadProducto;
      
      const detalle: DetalleCompra = {
        producto: this.productoSeleccionado,
        cantidad: this.cantidadProducto,
        precioUnitario: this.precioUnitario,
        subtotal: subtotal
      };
      
      this.detallesCompra.push(detalle);
      this.productoSeleccionado = null;
      this.cantidadProducto = 1;
      this.precioUnitario = 0;
    }
  }

  removerDetalle(index: number): void {
    this.detallesCompra.splice(index, 1);
  }

  calcularTotal(): number {
    return this.detallesCompra.reduce((total, detalle) => total + detalle.subtotal!, 0);
  }

  guardarCompra(): void {
    if (this.compraForm.valid && this.detallesCompra.length > 0) {
      const compraData = {
        proveedorId: this.compraForm.value.proveedorId,
        detalles: this.detallesCompra.map(detalle => ({
          productoId: detalle.producto?.id,
          cantidad: detalle.cantidad,
          precioUnitario: detalle.precioUnitario
        }))
      };
      
      this.compraService.crear(compraData).subscribe({
        next: () => {
          this.cargarCompras();
          this.cerrarModal();
        },
        error: (error) => console.error('Error creando compra:', error)
      });
    }
  }

  verDetalle(compra: Compra): void {
    this.compraSeleccionada = compra;
    this.mostrarModalDetalle = true;
  }

  marcarRecibida(compra: Compra): void {
    this.compraService.cambiarEstado(compra.id!, EstadoCompra.RECIBIDA).subscribe({
      next: () => this.cargarCompras(),
      error: (error) => console.error('Error cambiando estado:', error)
    });
  }

  cancelarCompra(compra: Compra): void {
    if (confirm('¿Está seguro de cancelar esta compra?')) {
      this.compraService.cancelar(compra.id!).subscribe({
        next: () => this.cargarCompras(),
        error: (error) => console.error('Error cancelando compra:', error)
      });
    }
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'warning';
      case 'RECIBIDA': return 'active';
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