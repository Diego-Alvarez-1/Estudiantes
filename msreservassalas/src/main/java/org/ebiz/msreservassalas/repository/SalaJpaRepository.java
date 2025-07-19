package org.ebiz.msreservassalas.repository;

import org.ebiz.msreservassalas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaJpaRepository extends JpaRepository<Sala, Long> {
    Optional<Sala> findById(Long id);
    List<Sala> findAllByHabilitado(Integer habilitado);
}