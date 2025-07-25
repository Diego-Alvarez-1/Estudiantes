import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const AuthGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  console.log('AuthGuard ejecutándose...'); // Para debug
  console.log('Usuario logueado:', authService.isLoggedIn()); // Para debug

  if (authService.isLoggedIn()) {
    console.log('Acceso permitido'); // Para debug
    return true;
  }

  console.log('Redirigiendo a login'); // Para debug
  router.navigate(['/login']);
  return false;
};