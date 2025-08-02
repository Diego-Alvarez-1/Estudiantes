package com.diegoAlvarez.sistema_reserva_libros.service;

import com.diegoAlvarez.sistema_reserva_libros.dto.ReservaDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Reserva;

import java.util.List;

public interface ReservaService {
    Reserva crearReserva(ReservaDTO reservaDTO);
    List<Reserva> obtenerReservasPorUsuario(Long usuarioId);
}