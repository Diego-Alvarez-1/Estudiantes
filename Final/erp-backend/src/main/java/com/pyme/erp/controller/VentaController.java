package com.pyme.erp.controller;

import com.pyme.erp.dto.request.VentaRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Venta;
import com.pyme.erp.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Venta>> crear(
            @Valid @RequestBody VentaRequest request) {
        log.info("Creando nueva venta para cliente ID: {}", request.getClienteId());
        
        Venta venta = ventaService.crear(request);
        ResponseWrapper<Venta> response = ResponseWrapper.success(
            venta, 
            "Venta creada exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Venta>>> listar() {
        log.info("Listando todas las ventas");
        
        List<Venta> ventas = ventaService.listarTodas();
        ResponseWrapper<List<Venta>> response = ResponseWrapper.success(
            ventas, 
            "Ventas obtenidas exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Venta>> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo venta por ID: {}", id);
        
        Venta venta = ventaService.obtenerPorId(id);
        ResponseWrapper<Venta> response = ResponseWrapper.success(
            venta, 
            "Venta encontrada"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ResponseWrapper<List<Venta>>> obtenerPorCliente(
            @PathVariable Long clienteId) {
        log.info("Obteniendo ventas por cliente ID: {}", clienteId);
        
        List<Venta> ventas = ventaService.obtenerPorCliente(clienteId);
        ResponseWrapper<List<Venta>> response = ResponseWrapper.success(
            ventas, 
            "Ventas del cliente obtenidas"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ResponseWrapper<List<Venta>>> obtenerPorEstado(
            @PathVariable Venta.EstadoVenta estado) {
        log.info("Obteniendo ventas por estado: {}", estado);
        
        List<Venta> ventas = ventaService.obtenerPorEstado(estado);
        ResponseWrapper<List<Venta>> response = ResponseWrapper.success(
            ventas, 
            "Ventas filtradas por estado"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<ResponseWrapper<List<Venta>>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        log.info("Obteniendo ventas entre {} y {}", fechaInicio, fechaFin);
        
        List<Venta> ventas = ventaService.obtenerPorRangoFechas(fechaInicio, fechaFin);
        ResponseWrapper<List<Venta>> response = ResponseWrapper.success(
            ventas, 
            "Ventas filtradas por rango de fechas"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ResponseWrapper<Venta>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Venta.EstadoVenta estado) {
        log.info("Cambiando estado de venta ID: {} a {}", id, estado);
        
        Venta venta = ventaService.cambiarEstado(id, estado);
        ResponseWrapper<Venta> response = ResponseWrapper.success(
            venta, 
            "Estado de venta actualizado"
        );
        
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> cancelar(@PathVariable Long id) {
        log.info("Cancelando venta ID: {}", id);
        
        ventaService.cancelar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Venta cancelada exitosamente"
        );
        
        return response.toResponseEntity();
    }
}