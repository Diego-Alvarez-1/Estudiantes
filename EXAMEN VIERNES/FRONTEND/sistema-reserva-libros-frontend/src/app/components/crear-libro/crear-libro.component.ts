import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LibroService } from '../../services/libro.service';
import { LibroDTO } from '../../models/libro.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-libro',
  templateUrl: './crear-libro.component.html',
  styleUrls: ['./crear-libro.component.css']
})
export class CrearLibroComponent {
  formLibro: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly libroService: LibroService,
    private readonly router: Router
  ) {
    this.formLibro = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(200)]],
      codigo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      autor: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      stock: ['', [Validators.required, Validators.min(0)]]
    });
  }

  crearLibro(): void {
    if (!this.formLibro.valid) {
      this.markAllFieldsAsTouched();
      return;
    }

    const libroDTO: LibroDTO = this.formLibro.getRawValue();

    this.libroService.crearLibro(libroDTO).subscribe({
      next: (response) => {
        if (response.estado === 'SUCCESS') {
          Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: response.mensaje,
            timer: 2000,
            showConfirmButton: false
          }).then(() => {
            this.router.navigate(['/libros']);
          });
        }
      },
      error: (error) => {
        console.error('Error al crear libro:', error);
        let errorMessage = 'Error al crear el libro';
        
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
    Object.keys(this.formLibro.controls).forEach(key => {
      this.formLibro.get(key)?.markAsTouched();
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.formLibro.get(fieldName);
    return !!(field && field.invalid && field.touched);
  }

  getFieldError(fieldName: string): string {
    const field = this.formLibro.get(fieldName);
    if (field && field.errors && field.touched) {
      if (field.errors['required']) return `${fieldName} es obligatorio`;
      if (field.errors['minlength']) return `${fieldName} debe tener al menos ${field.errors['minlength'].requiredLength} caracteres`;
      if (field.errors['maxlength']) return `${fieldName} no puede tener más de ${field.errors['maxlength'].requiredLength} caracteres`;
      if (field.errors['min']) return `${fieldName} debe ser mayor o igual a ${field.errors['min'].min}`;
    }
    return '';
  }
}