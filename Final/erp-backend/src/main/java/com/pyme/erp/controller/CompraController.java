package com.pyme.erp.controller;

import com.pyme.erp.dto.request.CompraRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Compra;
import com.pyme.erp.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Compra>> crear(
            @Valid @RequestBody CompraRequest request) {
        log.info("Creando nueva compra para proveedor ID: {}", request.getProveedorId());
        
        Compra compra = compraService.crear(request);
        ResponseWrapper<Compra> response = ResponseWrapper.success(
            compra, 
            "Compra creada exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Compra>>> listar() {
        log.info("Listando todas las compras");
        
        List<Compra> compras = compraService.listarTodas();
        ResponseWrapper<List<Compra>> response = ResponseWrapper.success(
            compras, 
            "Compras obtenidas exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Compra>> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo compra por ID: {}", id);
        
        Compra compra = compraService.obtenerPorId(id);
        ResponseWrapper<Compra> response = ResponseWrapper.success(
            compra, 
            "Compra encontrada"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<ResponseWrapper<List<Compra>>> obtenerPorProveedor(
            @PathVariable Long proveedorId) {
        log.info("Obteniendo compras por proveedor ID: {}", proveedorId);
        
        List<Compra> compras = compraService.obtenerPorProveedor(proveedorId);
        ResponseWrapper<List<Compra>> response = ResponseWrapper.success(
            compras, 
            "Compras del proveedor obtenidas"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ResponseWrapper<List<Compra>>> obtenerPorEstado(
            @PathVariable Compra.EstadoCompra estado) {
        log.info("Obteniendo compras por estado: {}", estado);
        
        List<Compra> compras = compraService.obtenerPorEstado(estado);
        ResponseWrapper<List<Compra>> response = ResponseWrapper.success(
            compras, 
            "Compras filtradas por estado"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<ResponseWrapper<List<Compra>>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        log.info("Obteniendo compras entre {} y {}", fechaInicio, fechaFin);
        
        List<Compra> compras = compraService.obtenerPorRangoFechas(fechaInicio, fechaFin);
        ResponseWrapper<List<Compra>> response = ResponseWrapper.success(
            compras, 
            "Compras filtradas por rango de fechas"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ResponseWrapper<Compra>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Compra.EstadoCompra estado) {
        log.info("Cambiando estado de compra ID: {} a {}", id, estado);
        
        Compra compra = compraService.cambiarEstado(id, estado);
        ResponseWrapper<Compra> response = ResponseWrapper.success(
            compra, 
            "Estado de compra actualizado"
        );
        
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> cancelar(@PathVariable Long id) {
        log.info("Cancelando compra ID: {}", id);
        
        compraService.cancelar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Compra cancelada exitosamente"
        );
        
        return response.toResponseEntity();
    }
}