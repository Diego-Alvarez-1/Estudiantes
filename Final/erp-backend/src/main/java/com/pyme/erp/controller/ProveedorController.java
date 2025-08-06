package com.pyme.erp.controller;

import com.pyme.erp.dto.request.ProveedorRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Proveedor;
import com.pyme.erp.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Proveedor>> crear(
            @Valid @RequestBody ProveedorRequest request) {
        log.info("Creando nuevo proveedor: {}", request.getNombre());
        
        Proveedor proveedor = proveedorService.crear(request);
        ResponseWrapper<Proveedor> response = ResponseWrapper.success(
            proveedor, 
            "Proveedor creado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Proveedor>>> listar() {
        log.info("Listando proveedores activos");
        
        List<Proveedor> proveedores = proveedorService.listarActivos();
        ResponseWrapper<List<Proveedor>> response = ResponseWrapper.success(
            proveedores, 
            "Proveedores obtenidos exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Proveedor>> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo proveedor por ID: {}", id);
        
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        ResponseWrapper<Proveedor> response = ResponseWrapper.success(
            proveedor, 
            "Proveedor encontrado"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Proveedor>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequest request) {
        log.info("Actualizando proveedor ID: {}", id);
        
        Proveedor proveedor = proveedorService.actualizar(id, request);
        ResponseWrapper<Proveedor> response = ResponseWrapper.success(
            proveedor, 
            "Proveedor actualizado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> desactivar(@PathVariable Long id) {
        log.info("Desactivando proveedor ID: {}", id);
        
        proveedorService.desactivar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Proveedor desactivado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<ResponseWrapper<Void>> activar(@PathVariable Long id) {
        log.info("Activando proveedor ID: {}", id);
        
        proveedorService.activar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Proveedor activado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseWrapper<List<Proveedor>>> buscarPorNombre(
            @RequestParam String nombre) {
        log.info("Buscando proveedores por nombre: {}", nombre);
        
        List<Proveedor> proveedores = proveedorService.buscarPorNombre(nombre);
        ResponseWrapper<List<Proveedor>> response = ResponseWrapper.success(
            proveedores, 
            "Búsqueda completada"
        );
        
        return response.toResponseEntity();
    }
}