package org.ebiz.msreservassalas.service;

import org.ebiz.msreservassalas.model.Reserva;

import java.util.List;

public interface ReservaServiceInterface {
    Reserva crearReserva(Reserva reserva);
    List<Reserva> listar();
    List<Reserva> listarPorEstudiante(Long estudianteId);
    List<Reserva> listarPorSala(Long salaId);
    Reserva obtenerPorId(Long id);
    void cancelarReserva(Long id);
}