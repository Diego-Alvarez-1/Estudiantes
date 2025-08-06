package com.pyme.erp.controller;

import com.pyme.erp.dto.request.ProductoRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Producto;
import com.pyme.erp.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Producto>> crear(
            @Valid @RequestBody ProductoRequest request) {
        log.info("Creando nuevo producto: {}", request.getNombre());
        
        Producto producto = productoService.crear(request);
        ResponseWrapper<Producto> response = ResponseWrapper.success(
            producto, 
            "Producto creado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Producto>>> listar() {
        log.info("Listando productos activos");
        
        List<Producto> productos = productoService.listarActivos();
        ResponseWrapper<List<Producto>> response = ResponseWrapper.success(
            productos, 
            "Productos obtenidos exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Producto>> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo producto por ID: {}", id);
        
        Producto producto = productoService.obtenerPorId(id);
        ResponseWrapper<Producto> response = ResponseWrapper.success(
            producto, 
            "Producto encontrado"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Producto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        log.info("Actualizando producto ID: {}", id);
        
        Producto producto = productoService.actualizar(id, request);
        ResponseWrapper<Producto> response = ResponseWrapper.success(
            producto, 
            "Producto actualizado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> desactivar(@PathVariable Long id) {
        log.info("Desactivando producto ID: {}", id);
        
        productoService.desactivar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Producto desactivado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<ResponseWrapper<List<Producto>>> obtenerStockBajo() {
        log.info("Obteniendo productos con stock bajo");
        
        List<Producto> productos = productoService.obtenerProductosConStockBajo();
        ResponseWrapper<List<Producto>> response = ResponseWrapper.success(
            productos, 
            "Productos con stock bajo obtenidos"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ResponseWrapper<Void>> actualizarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestParam String operacion) {
        log.info("Actualizando stock producto ID: {} - Operación: {} - Cantidad: {}", 
                id, operacion, cantidad);
        
        productoService.actualizarStock(id, cantidad, operacion);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Stock actualizado exitosamente"
        );
        
        return response.toResponseEntity();
    }
}