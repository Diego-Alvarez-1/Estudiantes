package org.ebiz.msreservassalas.repository;

import org.ebiz.msreservassalas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaJpaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findById(Long id);
    List<Reserva> findAllByEstudianteId(Long estudianteId);
    List<Reserva> findAllBySalaId(Long salaId);
    List<Reserva> findAllByEstado(String estado);
    
    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId AND r.fechaReserva = :fechaReserva AND r.estado = 'ACTIVA'")
    Optional<Reserva> findBySalaAndFechaAndEstadoActiva(@Param("salaId") Long salaId, @Param("fechaReserva") LocalDateTime fechaReserva);
    
    @Query("SELECT r FROM Reserva r WHERE r.estudiante.id = :estudianteId AND r.fechaReserva = :fechaReserva AND r.estado = 'ACTIVA'")
    Optional<Reserva> findByEstudianteAndFechaAndEstadoActiva(@Param("estudianteId") Long estudianteId, @Param("fechaReserva") LocalDateTime fechaReserva);
}