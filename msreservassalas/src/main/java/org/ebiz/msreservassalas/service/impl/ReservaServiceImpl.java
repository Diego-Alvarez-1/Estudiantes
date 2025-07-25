package org.ebiz.msreservassalas.service.impl;

import org.ebiz.msreservassalas.model.Estudiante;
import org.ebiz.msreservassalas.model.Reserva;
import org.ebiz.msreservassalas.model.Sala;
import org.ebiz.msreservassalas.repository.EstudianteJpaRepository;
import org.ebiz.msreservassalas.repository.ReservaJpaRepository;
import org.ebiz.msreservassalas.repository.SalaJpaRepository;
import org.ebiz.msreservassalas.service.ReservaServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaServiceInterface {

    private final ReservaJpaRepository repo;
    private final EstudianteJpaRepository estudianteRepo;
    private final SalaJpaRepository salaRepo;

    public ReservaServiceImpl(
            ReservaJpaRepository reservaJpaRepository,
            EstudianteJpaRepository estudianteJpaRepository,
            SalaJpaRepository salaJpaRepository
    ) {
        this.repo = reservaJpaRepository;
        this.estudianteRepo = estudianteJpaRepository;
        this.salaRepo = salaJpaRepository;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        // Validar que el estudiante existe
        Estudiante estudiante = estudianteRepo.findById(reserva.getEstudiante().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validar que la sala existe
        Sala sala = salaRepo.findById(reserva.getSala().getId())
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // Validar que la cantidad de estudiantes no exceda la capacidad
        if (reserva.getCantidadEstudiantes() > sala.getCapacidad()) {
            throw new RuntimeException("La cantidad de estudiantes excede la capacidad de la sala");
        }

        // Validar que la sala no esté ocupada en esa fecha
        Optional<Reserva> salaOcupada = repo.findBySalaAndFechaAndEstadoActiva(
                sala.getId(), 
                reserva.getFechaReserva()
        );
        if (salaOcupada.isPresent()) {
            throw new RuntimeException("La sala ya está ocupada en esa fecha y hora");
        }

        // Validar que el estudiante no tenga otra reserva al mismo tiempo
        Optional<Reserva> estudianteOcupado = repo.findByEstudianteAndFechaAndEstadoActiva(
                estudiante.getId(), 
                reserva.getFechaReserva()
        );
        if (estudianteOcupado.isPresent()) {
            throw new RuntimeException("El estudiante ya tiene una reserva en esa fecha y hora");
        }

        // Establecer valores por defecto
        reserva.setEstudiante(estudiante);
        reserva.setSala(sala);
        reserva.setEstado("ACTIVA");
        reserva.setFechaCreacion(LocalDateTime.now());

        return repo.save(reserva);
    }

    @Override
    public List<Reserva> listar() {
        return repo.findAll();
    }

    @Override
    public List<Reserva> listarPorEstudiante(Long estudianteId) {
        return repo.findAllByEstudianteId(estudianteId);
    }

    @Override
    public List<Reserva> listarPorSala(Long salaId) {
        return repo.findAllBySalaId(salaId);
    }

    @Override
    public Reserva obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void cancelarReserva(Long id) {
        Reserva reserva = repo.findById(id).orElse(null);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }

        // Validar que se pueda cancelar (al menos 1 hora de anticipación)
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limiteCancelacion = reserva.getFechaReserva().minusHours(1);
        
        if (ahora.isAfter(limiteCancelacion)) {
            throw new RuntimeException("No se puede cancelar la reserva. Debe hacerlo con al menos 1 hora de anticipación");
        }

        reserva.setEstado("CANCELADA");
        repo.save(reserva);
    }
}