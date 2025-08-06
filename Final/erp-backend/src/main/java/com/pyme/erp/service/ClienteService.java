package com.pyme.erp.service;

import com.pyme.erp.dto.request.ClienteRequest;
import com.pyme.erp.model.Cliente;

import java.util.List;

public interface ClienteService {
    
    Cliente crear(ClienteRequest request);
    
    Cliente actualizar(Long id, ClienteRequest request);
    
    Cliente obtenerPorId(Long id);
    
    List<Cliente> listarActivos();
    
    void desactivar(Long id);
    
    void activar(Long id);
    
    List<Cliente> buscarPorNombre(String nombre);
}