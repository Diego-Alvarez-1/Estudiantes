import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { UsuarioDTO } from '../../models/usuario.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-usuario',
  templateUrl: './crear-usuario.component.html',
  styleUrls: ['./crear-usuario.component.css']
})
export class CrearUsuarioComponent {
  formUsuario: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly usuarioService: UsuarioService,
    private readonly router: Router
  ) {
    this.formUsuario = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  crearUsuario(): void {
    if (!this.formUsuario.valid) {
      this.markAllFieldsAsTouched();
      return;
    }

    const usuarioDTO: UsuarioDTO = this.formUsuario.getRawValue();

    this.usuarioService.crearUsuario(usuarioDTO).subscribe({
      next: (response) => {
        if (response.estado === 'SUCCESS') {
          Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: response.mensaje,
            timer: 2000,
            showConfirmButton: false
          }).then(() => {
            this.router.navigate(['/usuarios']);
          });
        }
      },
      error: (error) => {
        console.error('Error al crear usuario:', error);
        let errorMessage = 'Error al crear el usuario';
        
        if (error.error?.mensaje) {
          errorMessage = error.error.mensaje;
        } else if (error.error?.errores && error.error.errores.length > 0) {
          errorMessage = error.error.errores.map((e: any) => e.mensaje).join(', ');
        }

        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: errorMessage
        });
      }
    });
  }

  private markAllFieldsAsTouched(): void {
    Object.keys(this.formUsuario.controls).forEach(key => {
      this.formUsuario.get(key)?.markAsTouched();
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.formUsuario.get(fieldName);
    return !!(field && field.invalid && field.touched);
  }

  getFieldError(fieldName: string): string {
    const field = this.formUsuario.get(fieldName);
    if (field && field.errors && field.touched) {
      if (field.errors['required']) return `${fieldName} es obligatorio`;
      if (field.errors['minlength']) return `${fieldName} debe tener al menos ${field.errors['minlength'].requiredLength} caracteres`;
      if (field.errors['maxlength']) return `${fieldName} no puede tener más de ${field.errors['maxlength'].requiredLength} caracteres`;
      if (field.errors['email']) return 'Debe ser un correo electrónico válido';
    }
    return '';
  }
}