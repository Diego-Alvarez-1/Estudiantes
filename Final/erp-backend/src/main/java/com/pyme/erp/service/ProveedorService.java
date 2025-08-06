package com.pyme.erp.service;

import com.pyme.erp.dto.request.ProveedorRequest;
import com.pyme.erp.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    
    Proveedor crear(ProveedorRequest request);
    
    Proveedor actualizar(Long id, ProveedorRequest request);
    
    Proveedor obtenerPorId(Long id);
    
    List<Proveedor> listarActivos();
    
    void desactivar(Long id);
    
    void activar(Long id);
    
    List<Proveedor> buscarPorNombre(String nombre);
}