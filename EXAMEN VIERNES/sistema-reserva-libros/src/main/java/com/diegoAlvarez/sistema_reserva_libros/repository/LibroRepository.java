package com.diegoAlvarez.sistema_reserva_libros.repository;

import com.diegoAlvarez.sistema_reserva_libros.model.Libro;
import com.diegoAlvarez.sistema_reserva_libros.model.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Libro> findByDisponibleTrueAndEstado(EstadoLibro estado);
    List<Libro> findByDisponibleTrue();
}