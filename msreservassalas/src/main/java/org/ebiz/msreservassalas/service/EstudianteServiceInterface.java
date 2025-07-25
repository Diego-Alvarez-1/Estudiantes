package org.ebiz.msreservassalas.service;

import org.ebiz.msreservassalas.model.Estudiante;

import java.util.List;

public interface EstudianteServiceInterface {
    Estudiante guardar(Estudiante estudiante);
    List<Estudiante> listar();
    Estudiante obtenerPorId(Long id);
    Estudiante actualizar(Long id, Estudiante estudiante);
    void eliminar(Long id);
    void cambiarEstado(Long id, boolean habilitado);
}