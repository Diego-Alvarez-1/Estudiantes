import { Usuario } from './usuario.model';
import { Libro } from './libro.model';

export interface Reserva {
  id?: number;
  usuario: Usuario;
  libro: Libro;
  fechaReserva: string;
}

export interface ReservaDTO {
  usuarioId: number;
  libroId: number;
  fechaReserva: string;
}