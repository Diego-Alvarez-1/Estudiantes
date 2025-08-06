package com.pyme.erp.repository;

import com.pyme.erp.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    
    Optional<Compra> findByNumeroOrden(String numeroOrden);
    
    List<Compra> findByProveedorId(Long proveedorId);
    
    List<Compra> findByEstado(Compra.EstadoCompra estado);
    
    @Query("SELECT c FROM Compra c WHERE c.fechaCompra BETWEEN ?1 AND ?2")
    List<Compra> findComprasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}