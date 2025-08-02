package com.diegoAlvarez.sistema_reserva_libros.service;

import com.diegoAlvarez.sistema_reserva_libros.dto.LibroDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Libro;

import java.util.List;

public interface LibroService {
    Libro crearLibro(LibroDTO libroDTO);
    List<Libro> listarLibros();
    List<Libro> listarLibrosDisponibles();
    Libro obtenerLibroPorId(Long id);
}