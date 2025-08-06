package com.pyme.erp.repository;

import com.pyme.erp.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    Optional<Venta> findByNumeroFactura(String numeroFactura);
    
    List<Venta> findByClienteId(Long clienteId);
    
    List<Venta> findByEstado(Venta.EstadoVenta estado);
    
    @Query("SELECT v FROM Venta v WHERE v.fechaVenta BETWEEN ?1 AND ?2")
    List<Venta> findVentasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}