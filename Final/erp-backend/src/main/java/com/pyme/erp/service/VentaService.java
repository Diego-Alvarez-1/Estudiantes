package com.pyme.erp.service;

import com.pyme.erp.dto.request.VentaRequest;
import com.pyme.erp.model.Venta;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaService {
    
    Venta crear(VentaRequest request);
    
    Venta obtenerPorId(Long id);
    
    List<Venta> listarTodas();
    
    List<Venta> obtenerPorCliente(Long clienteId);
    
    List<Venta> obtenerPorEstado(Venta.EstadoVenta estado);
    
    List<Venta> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    Venta cambiarEstado(Long id, Venta.EstadoVenta nuevoEstado);
    
    void cancelar(Long id);
}