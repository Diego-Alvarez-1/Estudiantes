package com.pyme.erp.service;

import com.pyme.erp.dto.request.ProductoRequest;
import com.pyme.erp.model.Producto;

import java.util.List;

public interface ProductoService {
    
    Producto crear(ProductoRequest request);
    
    Producto actualizar(Long id, ProductoRequest request);
    
    Producto obtenerPorId(Long id);
    
    List<Producto> listarActivos();
    
    void desactivar(Long id);
    
    void activar(Long id);
    
    List<Producto> buscarPorNombre(String nombre);
    
    List<Producto> obtenerProductosConStockBajo();
    
    void actualizarStock(Long productoId, Integer cantidad, String operacion);
}