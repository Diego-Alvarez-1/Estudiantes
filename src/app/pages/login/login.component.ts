import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginServiceService } from '../../service/login-service.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  // CORRECCIÓN: Agregué formEstudiante que usabas en el método guardar
  formEstudiante: FormGroup;
  
  constructor(private readonly fb: FormBuilder, private readonly _service: LoginServiceService) {
    // Form para login (si lo necesitas)
    this.form = fb.group({
      usuario: ['', Validators.required],    
      contrasenia: ['', Validators.required] 
    });
    
    this.formEstudiante = fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      carrera: ['', Validators.required],
      habilitado: [true, Validators.required],
    });
  }

  ngOnInit(): void {
    console.log("Form login =>", this.form.getRawValue());
    console.log("Form estudiante =>", this.formEstudiante.getRawValue());
  }

  iniciarSesion() {
    console.log("Datos login =>", this.form.getRawValue());
    console.log("usuario =>", this.form.controls['usuario'].value);
    console.log("contrasenia =>", this.form.controls['contrasenia'].value);
    console.log("Formulario válido =>", this.form.valid);
    
    if (this.form.valid) {
      // Aquí puedes agregar la lógica de login si la necesitas
      console.log("Realizando login...");
    }
  }
  
  // Tu método guardar corregido
  guardar() {
    if (this.formEstudiante.valid) {
      this._service.guardarAlumnos(this.formEstudiante.getRawValue()).subscribe({
        next: (r) => {
          console.log("Estudiante guardado:", r);
          // MEJORA: Resetear el formulario después de guardar
          this.formEstudiante.reset();
          // MEJORA: Mostrar mensaje de éxito
          alert('Estudiante guardado exitosamente');
        },
        error: (e) => {
          console.error("Error al guardar estudiante:", e);
          // MEJORA: Mostrar mensaje de error
          alert('Error al guardar estudiante: ' + e.message);
        }
      });
    } else {
      console.log("Formulario inválido");
      alert('Por favor, completa todos los campos requeridos');
    }
  }
  
  // MÉTODOS ADICIONALES que puedes usar
  listarEstudiantes() {
    this._service.getLogin().subscribe({
      next: (estudiantes) => {
        console.log("Lista de estudiantes:", estudiantes);
      },
      error: (e) => {
        console.error("Error al listar estudiantes:", e);
      }
    });
  }
  
  eliminarEstudiante(id: string) {
    this._service.deleteLogin(id).subscribe({
      next: (r) => {
        console.log("Estudiante eliminado:", r);
      },
      error: (e) => {
        console.error("Error al eliminar estudiante:", e);
      }
    });
  }
  
  actualizarEstudiante(id: string) {
    if (this.formEstudiante.valid) {
      this._service.updateLogin(id, this.formEstudiante.getRawValue()).subscribe({
        next: (r) => {
          console.log("Estudiante actualizado:", r);
        },
        error: (e) => {
          console.error("Error al actualizar estudiante:", e);
        }
      });
    }
  }
}