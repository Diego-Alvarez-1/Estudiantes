package org.ebiz.msreservassalas.service.impl;

import org.ebiz.msreservassalas.model.Estudiante;
import org.ebiz.msreservassalas.repository.EstudianteJpaRepository;
import org.ebiz.msreservassalas.service.EstudianteServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteServiceInterface {

    private final EstudianteJpaRepository repo;

    public EstudianteServiceImpl(EstudianteJpaRepository estudianteJpaRepository) {
        this.repo = estudianteJpaRepository;
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        Optional<Estudiante> existePorCodigo = repo.findByCodigo(estudiante.getCodigo());
        if (existePorCodigo.isPresent()) {
            throw new RuntimeException("Código ya se encuentra registrado");
        }

        Optional<Estudiante> existePorCorreo = repo.findByCorreo(estudiante.getCorreo());
        if (existePorCorreo.isPresent()) {
            throw new RuntimeException("Correo ya se encuentra registrado");
        }

        estudiante.setHabilitado(1);
        return repo.save(estudiante);
    }

    @Override
    public List<Estudiante> listar() {
        return repo.findAllByHabilitado(1);
    }

    @Override
    public Estudiante obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Estudiante actualizar(Long id, Estudiante estudiante) {
        Estudiante entidadActual = repo.findById(id).orElse(null);
        if (entidadActual == null) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        entidadActual.setNombre(estudiante.getNombre());
        entidadActual.setCodigo(estudiante.getCodigo());
        entidadActual.setCarrera(estudiante.getCarrera());
        entidadActual.setCorreo(estudiante.getCorreo());
        return repo.save(entidadActual);
    }

    @Override
    public void eliminar(Long id) {
        Estudiante entidadActual = repo.findById(id).orElse(null);
        if (entidadActual == null) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        entidadActual.setHabilitado(0);
        repo.save(entidadActual);
    }

    @Override
    public void cambiarEstado(Long id, boolean habilitado) {
        Estudiante estudiante = repo.findById(id).orElse(null);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        estudiante.setHabilitado(habilitado ? 1 : 0);
        repo.save(estudiante);
    }
}