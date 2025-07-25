package org.ebiz.msreservassalas.repository;

import org.ebiz.msreservassalas.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteJpaRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findById(Long id);
    List<Estudiante> findAllByHabilitado(Integer habilitado);
    Optional<Estudiante> findByCodigo(String codigo);
    Optional<Estudiante> findByCorreo(String correo);
}