package org.ebiz.msreservassalas.service.impl;

import org.ebiz.msreservassalas.model.Sala;
import org.ebiz.msreservassalas.repository.SalaJpaRepository;
import org.ebiz.msreservassalas.service.SalaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaServiceImpl implements SalaServiceInterface {

    private final SalaJpaRepository repo;

    public SalaServiceImpl(SalaJpaRepository salaJpaRepository) {
        this.repo = salaJpaRepository;
    }

    @Override
    public Sala guardar(Sala sala) {
        sala.setHabilitado(1);
        if (sala.getDiasAnticipoCancelacion() == null) {
            sala.setDiasAnticipoCancelacion(1);
        }
        return repo.save(sala);
    }

    @Override
    public List<Sala> listar() {
        return repo.findAllByHabilitado(1);
    }

    @Override
    public Sala obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Sala actualizar(Long id, Sala sala) {
        Sala entidadActual = repo.findById(id).orElse(null);
        if (entidadActual == null) {
            throw new RuntimeException("Sala no encontrada");
        }
        entidadActual.setNombre(sala.getNombre());
        entidadActual.setCapacidad(sala.getCapacidad());
        entidadActual.setDiasAnticipoCancelacion(sala.getDiasAnticipoCancelacion());
        return repo.save(entidadActual);
    }

    @Override
    public void eliminar(Long id) {
        Sala entidadActual = repo.findById(id).orElse(null);
        if (entidadActual == null) {
            throw new RuntimeException("Sala no encontrada");
        }
        entidadActual.setHabilitado(0);
        repo.save(entidadActual);
    }

    @Override
    public void cambiarEstado(Long id, boolean habilitado) {
        Sala sala = repo.findById(id).orElse(null);
        if (sala == null) {
            throw new RuntimeException("Sala no encontrada");
        }
        sala.setHabilitado(habilitado ? 1 : 0);
        repo.save(sala);
    }
}