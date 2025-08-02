package com.diegoAlvarez.sistema_reserva_libros.service.impl;


import com.diegoAlvarez.sistema_reserva_libros.dto.LibroDTO;
import com.diegoAlvarez.sistema_reserva_libros.exception.EntityNotFoundException;
import com.diegoAlvarez.sistema_reserva_libros.model.Libro;
import com.diegoAlvarez.sistema_reserva_libros.model.EstadoLibro;
import com.diegoAlvarez.sistema_reserva_libros.repository.LibroRepository;
import com.diegoAlvarez.sistema_reserva_libros.service.LibroService;
import com.diegoAlvarez.sistema_reserva_libros.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    @Override
    public Libro crearLibro(LibroDTO libroDTO) {
        if (libroRepository.existsByCodigo(libroDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un libro con el código: " + libroDTO.getCodigo());
        }

        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitulo());
        libro.setCodigo(libroDTO.getCodigo());
        libro.setAutor(libroDTO.getAutor());
        libro.setStock(libroDTO.getStock());
        libro.setDisponible(libroDTO.getStock() > 0);
        libro.setEstado(libroDTO.getStock() > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.AGOTADO);

        return libroRepository.save(libro);
    }

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public List<Libro> listarLibrosDisponibles() {
        return libroRepository.findByDisponibleTrueAndEstado(EstadoLibro.DISPONIBLE);
    }

    @Override
    public Libro obtenerLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con ID: " + id));
    }
}