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
  clienteForm: FormGroup;
  editForm: FormGroup;
  clientes: Cliente[] = [];
  clienteEditar: Cliente | null = null;
  isEditing = false;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService
  ) {
    this.clienteForm = this.createForm();
    this.editForm = this.createForm();
  }

  ngOnInit(): void {
    this.listarClientes();
  }

  createForm(): FormGroup {
    return this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      telefono: [''],
      direccion: [''],
      rut: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  listarClientes(): void {
    this.loading = true;
    this.clienteService.listar().subscribe({
      next: (response) => {
        this.clientes = response.data || [];
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar clientes:', error);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.clienteForm.valid) {
      this.loading = true;
      this.clienteService.crear(this.clienteForm.value).subscribe({
        next: (response) => {
          this.clienteForm.reset();
          this.listarClientes();
          this.showMessage('Cliente creado exitosamente');
        },
        error: (error) => {
          console.error('Error al crear cliente:', error);
          this.loading = false;
        }
      });
    }
  }

  editarCliente(cliente: Cliente): void {
    this.clienteEditar = cliente;
    this.isEditing = true;
    this.editForm.patchValue(cliente);
  }

  onEdit(): void {
    if (this.editForm.valid && this.clienteEditar) {
      this.loading = true;
      this.clienteService.actualizar(this.clienteEditar.id!, this.editForm.value).subscribe({
        next: (response) => {
          this.cancelEdit();
          this.listarClientes();
          this.showMessage('Cliente actualizado exitosamente');
        },
        error: (error) => {
          console.error('Error al actualizar cliente:', error);
          this.loading = false;
        }
      });
    }
  }

  desactivarCliente(id: number): void {
    if (confirm('¿Está seguro de desactivar este cliente?')) {
      this.clienteService.desactivar(id).subscribe({
        next: () => {
          this.listarClientes();
          this.showMessage('Cliente desactivado exitosamente');
        },
        error: (error) => console.error('Error al desactivar cliente:', error)
      });
    }
  }

  activarCliente(id: number): void {
    this.clienteService.activar(id).subscribe({
      next: () => {
        this.listarClientes();
        this.showMessage('Cliente activado exitosamente');
      },
      error: (error) => console.error('Error al activar cliente:', error)
    });
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.clienteEditar = null;
    this.editForm.reset();
  }

  private showMessage(message: string): void {
    // Implementar notificación toast
    alert(message);
  }
}