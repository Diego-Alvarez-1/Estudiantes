import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ProductosService } from '../../services/productos.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit {
  productos: any[] = [];
  productoForm: FormGroup;
  productoSeleccionado: any = null;

  constructor(
    private formBuilder: FormBuilder,
    private productosService: ProductosService
  ) {
    this.productoForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      precio: ['', [Validators.required, Validators.min(0)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      imagen: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.cargarProductos();
  }

  get f() { return this.productoForm.controls; }

  cargarProductos() {
    this.productosService.obtenerProductos().subscribe(productos => {
      this.productos = productos;
    });
  }

  onSubmit() {
    if (this.productoForm.valid) {
      this.productosService.agregarProducto(this.productoForm.value);
      this.productoForm.reset();
      // Cerrar modal programáticamente
      const modal = document.getElementById('modalAgregarProducto');
      if (modal) {
        const bootstrapModal = (window as any).bootstrap.Modal.getInstance(modal);
        if (bootstrapModal) {
          bootstrapModal.hide();
        }
      }
    }
  }

  verDetalles(producto: any) {
    this.productoSeleccionado = producto;
  }
}