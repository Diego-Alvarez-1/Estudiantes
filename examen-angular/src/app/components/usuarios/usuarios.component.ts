import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UsuariosService } from '../../services/usuarios.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit {
  usuarios: any[] = [];
  usuariosFiltrados: any[] = [];
  usuarioForm: FormGroup;
  filtroNombre: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private usuariosService: UsuariosService
  ) {
    this.usuarioForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      rol: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.cargarUsuarios();
  }

  get f() { return this.usuarioForm.controls; }

  cargarUsuarios() {
    this.usuariosService.obtenerUsuarios().subscribe(usuarios => {
      this.usuarios = usuarios;
      this.usuariosFiltrados = usuarios;
    });
  }

  filtrarUsuarios(event: any) {
    this.filtroNombre = event.target.value.toLowerCase();
    this.usuariosFiltrados = this.usuarios.filter(usuario =>
      usuario.nombre.toLowerCase().includes(this.filtroNombre)
    );
  }

  onSubmit() {
    if (this.usuarioForm.valid) {
      this.usuariosService.agregarUsuario(this.usuarioForm.value);
      this.usuarioForm.reset();
      // Cerrar modal programáticamente
      const modal = document.getElementById('modalAgregarUsuario');
      if (modal) {
        const bootstrapModal = (window as any).bootstrap.Modal.getInstance(modal);
        if (bootstrapModal) {
          bootstrapModal.hide();
        }
      }
    }
  }

  getRolClass(rol: string): string {
    switch (rol) {
      case 'Administrador':
        return 'bg-danger text-white';
      case 'Editor':
        return 'bg-warning text-dark';
      case 'Usuario':
        return 'bg-success text-white';
      default:
        return 'bg-secondary text-white';
    }
  }
}