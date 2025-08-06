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
  productoForm: FormGroup;
  editForm: FormGroup;
  stockForm: FormGroup;
  productos: Producto[] = [];
  productoEditar: Producto | null = null;
  isEditing = false;
  showStockModal = false;
  selectedProducto: Producto | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService
  ) {
    this.productoForm = this.createProductoForm();
    this.editForm = this.createProductoForm();
    this.stockForm = this.fb.group({
      cantidad: ['', [Validators.required, Validators.min(1)]],
      operacion: ['ENTRADA', Validators.required]
    });
  }

  ngOnInit(): void {
    this.listarProductos();
  }

  createProductoForm(): FormGroup {
    return this.fb.group({
      codigo: ['', [Validators.required, Validators.minLength(3)]],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: [''],
      precio: ['', [Validators.required, Validators.min(0.01)]],
      stockActual: [0, [Validators.required, Validators.min(0)]],
      stockMinimo: [0, [Validators.required, Validators.min(0)]]
    });
  }

  listarProductos(): void {
    this.loading = true;
    this.productoService.listar().subscribe({
      next: (response) => {
        this.productos = response.data || [];
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar productos:', error);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.productoForm.valid) {
      this.loading = true;
      this.productoService.crear(this.productoForm.value).subscribe({
        next: (response) => {
          this.productoForm.reset();
          this.listarProductos();
          this.showMessage('Producto creado exitosamente');
        },
        error: (error) => {
          console.error('Error al crear producto:', error);
          this.loading = false;
        }
      });
    }
  }

  editarProducto(producto: Producto): void {
    this.productoEditar = producto;
    this.isEditing = true;
    this.editForm.patchValue(producto);
  }

  onEdit(): void {
    if (this.editForm.valid && this.productoEditar) {
      this.loading = true;
      this.productoService.actualizar(this.productoEditar.id!, this.editForm.value).subscribe({
        next: (response) => {
          this.cancelEdit();
          this.listarProductos();
          this.showMessage('Producto actualizado exitosamente');
        },
        error: (error) => {
          console.error('Error al actualizar producto:', error);
          this.loading = false;
        }
      });
    }
  }

  desactivarProducto(id: number): void {
    if (confirm('¿Está seguro de desactivar este producto?')) {
      this.productoService.desactivar(id).subscribe({
        next: () => {
          this.listarProductos();
          this.showMessage('Producto desactivado exitosamente');
        },
        error: (error) => console.error('Error al desactivar producto:', error)
      });
    }
  }

  abrirModalStock(producto: Producto): void {
    this.selectedProducto = producto;
    this.showStockModal = true;
    this.stockForm.reset({ cantidad: '', operacion: 'ENTRADA' });
  }

  actualizarStock(): void {
    if (this.stockForm.valid && this.selectedProducto) {
      const { cantidad, operacion } = this.stockForm.value;
      this.productoService.actualizarStock(this.selectedProducto.id!, cantidad, operacion).subscribe({
        next: () => {
          this.showStockModal = false;
          this.listarProductos();
          this.showMessage('Stock actualizado exitosamente');
        },
        error: (error) => console.error('Error al actualizar stock:', error)
      });
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.productoEditar = null;
    this.editForm.reset();
  }

  closeStockModal(): void {
    this.showStockModal = false;
    this.selectedProducto = null;
    this.stockForm.reset();
  }

  getStockStatus(producto: Producto): string {
    if (producto.stockActual! <= producto.stockMinimo!) {
      return 'low';
    }
    return 'normal';
  }

  private showMessage(message: string): void {
    alert(message);
  }
}