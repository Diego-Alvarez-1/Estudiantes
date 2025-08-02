export interface ApiResponse<T> {
  mensaje: string;
  estado: string;
  datos?: T;
  errores?: FieldErrorDetail[];
}

export interface FieldErrorDetail {
  campo: string;
  codigo: string;
  mensaje: string;
}