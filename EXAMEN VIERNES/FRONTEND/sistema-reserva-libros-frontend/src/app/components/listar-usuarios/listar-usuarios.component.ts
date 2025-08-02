import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { ReservaService } from '../../services/reserva.service';
import { Usuario } from '../../models/usuario.model';
import { Reserva } from '../../models/reserva.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-usuarios',
  templateUrl: './listar-usuarios.component.html',
  styleUrls: ['./listar-usuarios.component.css']
})
export class ListarUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  
  constructor(
    private readonly usuarioService: UsuarioService,
    private readonly reservaService: ReservaService
  ) { }

  ngOnInit(): void {
    this.listarUsuarios();
  }

  listarUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (response) => {
        if (response.estado === 'SUCCESS') {
          this.usuarios = response.datos || [];
        }
      },
      error: (error) => {
        console.error('Error al obtener usuarios:', error);
      }
    });
  }

  verReservasUsuario(usuario: Usuario): void {
    if (!usuario.id) return;
    
    this.reservaService.obtenerReservasPorUsuario(usuario.id).subscribe({
      next: (response) => {
        if (response.estado === 'SUCCESS') {
          const reservas = response.datos || [];
          
          if (reservas.length === 0) {
            Swal.fire({
              icon: 'info',
              title: 'Sin reservas',
              text: `${usuario.nombre} no tiene reservas registradas.`
            });
            return;
          }

          // Crear HTML para mostrar las reservas
          let reservasHtml = '<div class="table-responsive"><table class="table table-sm">';
          reservasHtml += '<thead><tr><th>Libro</th><th>Autor</th><th>Fecha Reserva</th></tr></thead><tbody>';
          
          reservas.forEach(reserva => {
            reservasHtml += `<tr>
              <td>${reserva.libro.titulo}</td>
              <td>${reserva.libro.autor}</td>
              <td>${new Date(reserva.fechaReserva).toLocaleDateString()}</td>
            </tr>`;
          });
          
          reservasHtml += '</tbody></table></div>';

          Swal.fire({
            title: `Reservas de ${usuario.nombre}`,
            html: reservasHtml,
            width: '600px',
            confirmButtonText: 'Cerrar'
          });
        }
      },
      error: (error) => {
        console.error('Error al obtener reservas:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron obtener las reservas del usuario.'
        });
      }
    });
  }
}