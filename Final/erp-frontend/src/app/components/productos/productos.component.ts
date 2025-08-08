import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  
  productos: Producto[] = [];
  mostrarModal = false;
  mostrarModalStock = false;
  modoEdicion = false;
  productoSeleccionado: Producto | null = null;
  
  productoForm: FormGroup;
  stockForm: FormGroup;

  constructor(
    private productoService: ProductoService,
    private fb: FormBuilder
  ) {
    this.productoForm = this.fb.group({
      codigo: ['', [Validators.required]],
      nombre: ['', [Validators.required]],
      descripcion: [''],
      precio: ['', [Validators.required, Validators.min(0)]],
      stockActual: [0, [Validators.min(0)]],
      stockMinimo: [0, [Validators.min(0)]]
    });

    this.stockForm = this.fb.group({
      operacion: ['ENTRADA', [Validators.required]],
      cantidad: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarProductos();
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
    this.modoEdicion = false;
    this.productoSeleccionado = null;
    this.productoForm.reset({
      stockActual: 0,
      stockMinimo: 0
    });
    this.mostrarModal = true;
  }

  editarProducto(producto: Producto): void {
    this.modoEdicion = true;
    this.productoSeleccionado = producto;
    this.productoForm.patchValue(producto);
    this.mostrarModal = true;
  }

  guardarProducto(): void {
    if (this.productoForm.valid) {
      const productoData = this.productoForm.value;
      
      if (this.modoEdicion && this.productoSeleccionado) {
        this.productoService.actualizar(this.productoSeleccionado.id!, productoData).subscribe({
          next: () => {
            this.cargarProductos();
            this.cerrarModal();
          },
          error: (error) => console.error('Error actualizando producto:', error)
        });
      } else {
        this.productoService.crear(productoData).subscribe({
          next: () => {
            this.cargarProductos();
            this.cerrarModal();
          },
          error: (error) => console.error('Error creando producto:', error)
        });
      }
    }
  }

  abrirModalStock(producto: Producto): void {
    this.productoSeleccionado = producto;
    this.stockForm.reset({
      operacion: 'ENTRADA'
    });
    this.mostrarModalStock = true;
  }

  actualizarStock(): void {
    if (this.stockForm.valid && this.productoSeleccionado) {
      const { cantidad, operacion } = this.stockForm.value;
      
      this.productoService.actualizarStock(
        this.productoSeleccionado.id!,
        cantidad,
        operacion
      ).subscribe({
        next: () => {
          this.cargarProductos();
          this.cerrarModalStock();
        },
        error: (error) => console.error('Error actualizando stock:', error)
      });
    }
  }

  cerrarModal(event?: any): void {
    if (event && event.target === event.currentTarget) {
      this.mostrarModal = false;
    } else if (!event) {
      this.mostrarModal = false;
    }
  }

  cerrarModalStock(event?: any): void {
    if (event && event.target === event.currentTarget) {
      this.mostrarModalStock = false;
    } else if (!event) {
      this.mostrarModalStock = false;
    }
  }
}