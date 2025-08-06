package com.pyme.erp.service;

import com.pyme.erp.dto.request.CompraRequest;
import com.pyme.erp.model.Compra;

import java.time.LocalDateTime;
import java.util.List;

public interface CompraService {
    
    Compra crear(CompraRequest request);
    
    Compra obtenerPorId(Long id);
    
    List<Compra> listarTodas();
    
    List<Compra> obtenerPorProveedor(Long proveedorId);
    
    List<Compra> obtenerPorEstado(Compra.EstadoCompra estado);
    
    List<Compra> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    Compra cambiarEstado(Long id, Compra.EstadoCompra nuevoEstado);
    
    void cancelar(Long id);
}