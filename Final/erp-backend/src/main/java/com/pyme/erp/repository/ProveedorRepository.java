package com.pyme.erp.repository;

import com.pyme.erp.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
    Optional<Proveedor> findByEmail(String email);
    
    Optional<Proveedor> findByRut(String rut);
    
    List<Proveedor> findByActivoTrue();
    
    @Query("SELECT p FROM Proveedor p WHERE p.nombre ILIKE %?1% AND p.activo = true")
    List<Proveedor> buscarPorNombre(String nombre);
}