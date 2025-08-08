import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/response-wrapper.model';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {
  
  clientes: Cliente[] = [];
  clientesFiltrados: Cliente[] = [];
  filtroNombre = '';
  
  mostrarModal = false;
  modoEdicion = false;
  clienteSeleccionado: Cliente | null = null;
  
  clienteForm: FormGroup;

  constructor(
    private clienteService: ClienteService,
    private fb: FormBuilder
  ) {
    this.clienteForm = this.fb.group({
      nombre: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      rut: ['', [Validators.required]],
      telefono: [''],
      direccion: ['']
    });
  }

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.listar().subscribe({
      next: (response) => {
        this.clientes = response.data;
        this.clientesFiltrados = [...this.clientes];
      },
      error: (error) => console.error('Error cargando clientes:', error)
    });
  }

  filtrarClientes(): void {
    if (this.filtroNombre.trim()) {
      this.clientesFiltrados = this.clientes.filter(cliente =>
        cliente.nombre.toLowerCase().includes(this.filtroNombre.toLowerCase())
      );
    } else {
      this.clientesFiltrados = [...this.clientes];
    }
  }

  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.clienteSeleccionado = null;
    this.clienteForm.reset();
    this.mostrarModal = true;
  }

  editarCliente(cliente: Cliente): void {
    this.modoEdicion = true;
    this.clienteSeleccionado = cliente;
    this.clienteForm.patchValue(cliente);
    this.mostrarModal = true;
  }

  guardarCliente(): void {
    if (this.clienteForm.valid) {
      const clienteData = this.clienteForm.value;
      
      if (this.modoEdicion && this.clienteSeleccionado) {
        this.clienteService.actualizar(this.clienteSeleccionado.id!, clienteData).subscribe({
          next: () => {
            this.cargarClientes();
            this.cerrarModal();
          },
          error: (error) => console.error('Error actualizando cliente:', error)
        });
      } else {
        this.clienteService.crear(clienteData).subscribe({
          next: () => {
            this.cargarClientes();
            this.cerrarModal();
          },
          error: (error) => console.error('Error creando cliente:', error)
        });
      }
    }
  }

  toggleEstado(cliente: Cliente): void {
    if (cliente.activo) {
      this.clienteService.desactivar(cliente.id!).subscribe({
        next: () => this.cargarClientes(),
        error: (error) => console.error('Error desactivando cliente:', error)
      });
    } else {
      this.clienteService.activar(cliente.id!).subscribe({
        next: () => this.cargarClientes(),
        error: (error) => console.error('Error activando cliente:', error)
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