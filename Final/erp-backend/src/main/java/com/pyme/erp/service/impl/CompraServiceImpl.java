package com.pyme.erp.service.impl;

import com.pyme.erp.dto.request.CompraRequest;
import com.pyme.erp.dto.request.DetalleCompraRequest;
import com.pyme.erp.exception.BusinessException;
import com.pyme.erp.exception.ErrorCode;
import com.pyme.erp.model.*;
import com.pyme.erp.repository.CompraRepository;
import com.pyme.erp.service.CompraService;
import com.pyme.erp.service.ProductoService;
import com.pyme.erp.service.ProveedorService;
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
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;

    @Override
    public Compra crear(CompraRequest request) {
        log.debug("Creando nueva compra para proveedor ID: {}", request.getProveedorId());
        
        // Obtener proveedor
        Proveedor proveedor = proveedorService.obtenerPorId(request.getProveedorId());
        
        // Crear compra
        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setNumeroOrden(generarNumeroOrden());
        
        // Crear detalles
        List<DetalleCompra> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        
        for (DetalleCompraRequest detalleRequest : request.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalleRequest.getProductoId());
            
            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(detalleRequest.getPrecioUnitario());
            // El subtotal se calcula automáticamente en el @PrePersist
            
            detalles.add(detalle);
            
            // Calcular total
            BigDecimal subtotal = detalleRequest.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
            total = total.add(subtotal);
        }
        
        compra.setDetalles(detalles);
        compra.setTotal(total);
        
        return compraRepository.save(compra);
    }

    @Override
    @Transactional(readOnly = true)
    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPRA_NO_ENCONTRADA));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> obtenerPorProveedor(Long proveedorId) {
        return compraRepository.findByProveedorId(proveedorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> obtenerPorEstado(Compra.EstadoCompra estado) {
        return compraRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return compraRepository.findComprasPorRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public Compra cambiarEstado(Long id, Compra.EstadoCompra nuevoEstado) {
        log.debug("Cambiando estado de compra ID: {} a {}", id, nuevoEstado);
        
        Compra compra = obtenerPorId(id);
        compra.setEstado(nuevoEstado);
        
        // Si se marca como RECIBIDA, actualizar stock de productos
        if (nuevoEstado == Compra.EstadoCompra.RECIBIDA) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                productoService.actualizarStock(
                    detalle.getProducto().getId(),
                    detalle.getCantidad(),
                    "ENTRADA"
                );
            }
        }
        
        return compraRepository.save(compra);
    }

    @Override
    public void cancelar(Long id) {
        log.debug("Cancelando compra ID: {}", id);
        
        Compra compra = obtenerPorId(id);
        
        if (compra.getEstado() == Compra.EstadoCompra.CANCELADA) {
            throw new BusinessException(ErrorCode.COMPRA_YA_CANCELADA);
        }
        
        compra.setEstado(Compra.EstadoCompra.CANCELADA);
        compraRepository.save(compra);
    }

    private String generarNumeroOrden() {
        // Generar número de orden basado en timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD-" + timestamp;
    }
}