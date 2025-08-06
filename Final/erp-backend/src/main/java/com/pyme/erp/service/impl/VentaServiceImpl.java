package com.pyme.erp.service.impl;

import com.pyme.erp.dto.request.DetalleVentaRequest;
import com.pyme.erp.dto.request.VentaRequest;
import com.pyme.erp.exception.BusinessException;
import com.pyme.erp.exception.ErrorCode;
import com.pyme.erp.model.*;
import com.pyme.erp.repository.VentaRepository;
import com.pyme.erp.service.ClienteService;
import com.pyme.erp.service.ProductoService;
import com.pyme.erp.service.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @Override
    public Venta crear(VentaRequest request) {
        log.debug("Creando nueva venta para cliente ID: {}", request.getClienteId());
        
        // Obtener cliente
        Cliente cliente = clienteService.obtenerPorId(request.getClienteId());
        
        // Crear venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setNumeroFactura(generarNumeroFactura());
        
        // Crear detalles
        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        
        for (DetalleVentaRequest detalleRequest : request.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalleRequest.getProductoId());
            
            // Validar stock disponible
            if (producto.getStockActual() < detalleRequest.getCantidad()) {
                throw new BusinessException(ErrorCode.STOCK_INSUFICIENTE);
            }
            
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            // El subtotal se calcula automáticamente en el @PrePersist
            
            detalles.add(detalle);
            
            // Calcular total
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
            total = total.add(subtotal);
            
            // Actualizar stock
            productoService.actualizarStock(producto.getId(), detalleRequest.getCantidad(), "SALIDA");
        }
        
        venta.setDetalles(detalles);
        venta.setTotal(total);
        
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Venta obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.VENTA_NO_ENCONTRADA));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> obtenerPorCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> obtenerPorEstado(Venta.EstadoVenta estado) {
        return ventaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findVentasPorRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public Venta cambiarEstado(Long id, Venta.EstadoVenta nuevoEstado) {
        log.debug("Cambiando estado de venta ID: {} a {}", id, nuevoEstado);
        
        Venta venta = obtenerPorId(id);
        venta.setEstado(nuevoEstado);
        
        return ventaRepository.save(venta);
    }

    @Override
    public void cancelar(Long id) {
        log.debug("Cancelando venta ID: {}", id);
        
        Venta venta = obtenerPorId(id);
        
        if (venta.getEstado() == Venta.EstadoVenta.CANCELADA) {
            throw new BusinessException(ErrorCode.VENTA_YA_CANCELADA);
        }
        
        // Devolver stock de productos
        for (DetalleVenta detalle : venta.getDetalles()) {
            productoService.actualizarStock(
                detalle.getProducto().getId(), 
                detalle.getCantidad(), 
                "ENTRADA"
            );
        }
        
        venta.setEstado(Venta.EstadoVenta.CANCELADA);
        ventaRepository.save(venta);
    }

    private String generarNumeroFactura() {
        // Generar número de factura basado en timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "FAC-" + timestamp;
    }
}