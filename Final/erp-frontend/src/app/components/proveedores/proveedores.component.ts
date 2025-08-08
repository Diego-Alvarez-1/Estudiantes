import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from '../../services/proveedor.service';
import { Proveedor } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-proveedores',
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent implements OnInit {
  
  proveedores: Proveedor[] = [];
  proveedoresFiltrados: Proveedor[] = [];
  filtroNombre = '';
  
  mostrarModal = false;
  modoEdicion = false;
  proveedorSeleccionado: Proveedor | null = null;
  
  proveedorForm: FormGroup;

  constructor(
    private proveedorService: ProveedorService,
    private fb: FormBuilder
  ) {
    this.proveedorForm = this.fb.group({
      nombre: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      rut: ['', [Validators.required]],
      telefono: [''],
      direccion: ['']
    });
  }

  ngOnInit(): void {
    this.cargarProveedores();
  }

  cargarProveedores(): void {
    this.proveedorService.listar().subscribe({
      next: (response) => {
        this.proveedores = response.data;
        this.proveedoresFiltrados = [...this.proveedores];
      },
      error: (error) => console.error('Error cargando proveedores:', error)
    });
  }

  filtrarProveedores(): void {
    if (this.filtroNombre.trim()) {
      this.proveedoresFiltrados = this.proveedores.filter(proveedor =>
        proveedor.nombre.toLowerCase().includes(this.filtroNombre.toLowerCase())
      );
    } else {
      this.proveedoresFiltrados = [...this.proveedores];
    }
  }

  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.proveedorSeleccionado = null;
    this.proveedorForm.reset();
    this.mostrarModal = true;
  }

  editarProveedor(proveedor: Proveedor): void {
    this.modoEdicion = true;
    this.proveedorSeleccionado = proveedor;
    this.proveedorForm.patchValue(proveedor);
    this.mostrarModal = true;
  }

  guardarProveedor(): void {
    if (this.proveedorForm.valid) {
      const proveedorData = this.proveedorForm.value;
      
      if (this.modoEdicion && this.proveedorSeleccionado) {
        this.proveedorService.actualizar(this.proveedorSeleccionado.id!, proveedorData).subscribe({
          next: () => {
            this.cargarProveedores();
            this.cerrarModal();
          },
          error: (error) => console.error('Error actualizando proveedor:', error)
        });
      } else {
        this.proveedorService.crear(proveedorData).subscribe({
          next: () => {
            this.cargarProveedores();
            this.cerrarModal();
          },
          error: (error) => console.error('Error creando proveedor:', error)
        });
      }
    }
  }

  toggleEstado(proveedor: Proveedor): void {
    if (proveedor.activo) {
      this.proveedorService.desactivar(proveedor.id!).subscribe({
        next: () => this.cargarProveedores(),
        error: (error) => console.error('Error desactivando proveedor:', error)
      });
    } else {
      this.proveedorService.activar(proveedor.id!).subscribe({
        next: () => this.cargarProveedores(),
        error: (error) => console.error('Error activando proveedor:', error)
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
}