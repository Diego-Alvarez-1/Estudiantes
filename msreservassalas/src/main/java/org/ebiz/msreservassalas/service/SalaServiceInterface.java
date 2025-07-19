package org.ebiz.msreservassalas.service;

import org.ebiz.msreservassalas.model.Sala;

import java.util.List;

public interface SalaServiceInterface {
    Sala guardar(Sala sala);
    List<Sala> listar();
    Sala obtenerPorId(Long id);
    Sala actualizar(Long id, Sala sala);
    void eliminar(Long id);
    void cambiarEstado(Long id, boolean habilitado);
}