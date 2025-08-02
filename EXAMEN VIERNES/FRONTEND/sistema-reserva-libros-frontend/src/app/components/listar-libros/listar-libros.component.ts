import { Component, OnInit } from '@angular/core';
import { LibroService } from '../../services/libro.service';
import { Libro } from '../../models/libro.model';

@Component({
  selector: 'app-listar-libros',
  templateUrl: './listar-libros.component.html',
  styleUrls: ['./listar-libros.component.css']
})
export class ListarLibrosComponent implements OnInit {
  libros: Libro[] = [];
  
  constructor(private readonly libroService: LibroService) { }

  ngOnInit(): void {
    this.listarLibros();
  }

  listarLibros(): void {
    this.libroService.listarLibros().subscribe({
      next: (response) => {
        if (response.estado === 'SUCCESS') {
          this.libros = response.datos || [];
        }
      },
      error: (error) => {
        console.error('Error al obtener libros:', error);
      }
    });
  }
}