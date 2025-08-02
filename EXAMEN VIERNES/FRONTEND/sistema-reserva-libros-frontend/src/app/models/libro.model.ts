export interface Libro {
  id?: number;
  titulo: string;
  codigo: string;
  autor: string;
  disponible: boolean;
  stock: number;
  estado: 'DISPONIBLE' | 'AGOTADO';
}

export interface LibroDTO {
  titulo: string;
  codigo: string;
  autor: string;
  stock: number;
}