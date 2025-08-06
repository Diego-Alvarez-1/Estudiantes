package com.pyme.erp.repository;

import com.pyme.erp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByCodigo(String codigo);
    
    List<Producto> findByActivoTrue();
    
    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
    
    @Query("SELECT p FROM Producto p WHERE p.nombre ILIKE %?1% AND p.activo = true")
    List<Producto> buscarPorNombre(String nombre);
}