package com.diegoAlvarez.sistema_reserva_libros.repository;

import com.diegoAlvarez.sistema_reserva_libros.model.Reserva;
import com.diegoAlvarez.sistema_reserva_libros.model.Usuario;
import com.diegoAlvarez.sistema_reserva_libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    Optional<Reserva> findByUsuarioAndLibro(Usuario usuario, Libro libro);
    boolean existsByUsuarioAndLibro(Usuario usuario, Libro libro);
    List<Reserva> findByUsuarioAndFechaReserva(Usuario usuario, LocalDate fechaReserva);
}