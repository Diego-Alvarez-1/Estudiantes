package com.pyme.erp.service.impl;

import com.pyme.erp.dto.request.ProductoRequest;
import com.pyme.erp.exception.BusinessException;
import com.pyme.erp.exception.ErrorCode;
import com.pyme.erp.model.Producto;
import com.pyme.erp.repository.ProductoRepository;
import com.pyme.erp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto crear(ProductoRequest request) {
        log.debug("Creando nuevo producto con código: {}", request.getCodigo());
        
        // Validar que no exista producto con mismo código
        Optional<Producto> productoPorCodigo = productoRepository.findByCodigo(request.getCodigo());
        if (productoPorCodigo.isPresent()) {
            throw new BusinessException(ErrorCode.PRODUCTO_CODIGO_DUPLICADO);
        }
        
        Producto producto = new Producto();
        producto.setCodigo(request.getCodigo());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStockActual(request.getStockActual());
        producto.setStockMinimo(request.getStockMinimo());
        
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, ProductoRequest request) {
        log.debug("Actualizando producto con ID: {}", id);
        
        Producto producto = obtenerPorId(id);
        
        // Validar código duplicado solo si cambió
        if (!producto.getCodigo().equals(request.getCodigo())) {
            Optional<Producto> productoPorCodigo = productoRepository.findByCodigo(request.getCodigo());
            if (productoPorCodigo.isPresent()) {
                throw new BusinessException(ErrorCode.PRODUCTO_CODIGO_DUPLICADO);
            }
        }
        
        producto.setCodigo(request.getCodigo());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStockActual(request.getStockActual());
        producto.setStockMinimo(request.getStockMinimo());
        
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCTO_NO_ENCONTRADO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    public void desactivar(Long id) {
        log.debug("Desactivando producto con ID: {}", id);
        Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    public void activar(Long id) {
        log.debug("Activando producto con ID: {}", id);
        Producto producto = obtenerPorId(id);
        producto.setActivo(true);
        productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductosConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    @Override
    public void actualizarStock(Long productoId, Integer cantidad, String operacion) {
        log.debug("Actualizando stock del producto ID: {} - Operación: {} - Cantidad: {}", 
                 productoId, operacion, cantidad);
        
        Producto producto = obtenerPorId(productoId);
        
        if ("ENTRADA".equals(operacion)) {
            producto.setStockActual(producto.getStockActual() + cantidad);
        } else if ("SALIDA".equals(operacion)) {
            int nuevoStock = producto.getStockActual() - cantidad;
            if (nuevoStock < 0) {
                throw new BusinessException(ErrorCode.STOCK_INSUFICIENTE);
            }
            producto.setStockActual(nuevoStock);
        }
        
        productoRepository.save(producto);
    }
}