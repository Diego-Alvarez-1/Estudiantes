import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.loginForm = this.formBuilder.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });

    // Redirigir si ya está logueado
    if (this.authService.currentUserValue) {
      this.router.navigate(['/home']);
    }
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(this.f['usuario'].value, this.f['password'].value)
      .subscribe({
        next: (user) => {
          if (user) {
            this.router.navigate(['/home']);
          } else {
            this.error = 'Usuario o contraseña incorrectos';
          }
        },
        error: (error) => {
          this.error = 'Error al iniciar sesión';
        }
      });
  }
}