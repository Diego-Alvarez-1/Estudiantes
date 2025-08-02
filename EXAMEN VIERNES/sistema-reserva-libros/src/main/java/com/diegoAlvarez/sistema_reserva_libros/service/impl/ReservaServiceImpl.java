package com.diegoAlvarez.sistema_reserva_libros.service.impl;

import com.diegoAlvarez.sistema_reserva_libros.dto.ReservaDTO;
import com.diegoAlvarez.sistema_reserva_libros.exception.*;
import com.diegoAlvarez.sistema_reserva_libros.model.*;
import com.diegoAlvarez.sistema_reserva_libros.repository.ReservaRepository;
import com.diegoAlvarez.sistema_reserva_libros.service.LibroService;
import com.diegoAlvarez.sistema_reserva_libros.service.ReservaService;
import com.diegoAlvarez.sistema_reserva_libros.service.UsuarioService;
import com.diegoAlvarez.sistema_reserva_libros.exception.LibroNoDisponibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioService usuarioService;
    private final LibroService libroService;

    @Override
    @Transactional
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(reservaDTO.getUsuarioId());
        Libro libro = libroService.obtenerLibroPorId(reservaDTO.getLibroId());

        // Validar que el libro esté disponible
        if (!libro.getDisponible() || libro.getEstado() == EstadoLibro.AGOTADO) {
            throw new LibroNoDisponibleException("El libro no está disponible para reserva");
        }

        // Validar que hay stock
        if (libro.getStock() <= 0) {
            throw new StockAgotadoException("No hay stock disponible para este libro");
        }

        // Validar que el usuario no haya reservado ya este libro
        if (reservaRepository.existsByUsuarioAndLibro(usuario, libro)) {
            throw new LibroYaReservadoException("El usuario ya tiene una reserva para este libro");
        }

        // Verificar si el usuario ya tiene una reserva para la misma fecha (bonus)
        if (!reservaRepository.findByUsuarioAndFechaReserva(usuario, reservaDTO.getFechaReserva()).isEmpty()) {
            throw new LibroYaReservadoException("El usuario ya tiene una reserva para esta fecha");
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setLibro(libro);
        reserva.setFechaReserva(reservaDTO.getFechaReserva());

        // Actualizar el stock del libro
        libro.setStock(libro.getStock() - 1);
        
        // Actualizar estado si es necesario
        if (libro.getStock() == 0) {
            libro.setDisponible(false);
            libro.setEstado(EstadoLibro.AGOTADO);
        }

        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }
}