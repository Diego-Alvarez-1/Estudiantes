package com.pyme.erp.controller;

import com.pyme.erp.dto.request.ClienteRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Cliente;
import com.pyme.erp.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Cliente>> crear(
            @Valid @RequestBody ClienteRequest request) {
        log.info("Creando nuevo cliente: {}", request.getNombre());
        
        Cliente cliente = clienteService.crear(request);
        ResponseWrapper<Cliente> response = ResponseWrapper.success(
            cliente, 
            "Cliente creado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Cliente>>> listar() {
        log.info("Listando clientes activos");
        
        List<Cliente> clientes = clienteService.listarActivos();
        ResponseWrapper<List<Cliente>> response = ResponseWrapper.success(
            clientes, 
            "Clientes obtenidos exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Cliente>> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo cliente por ID: {}", id);
        
        Cliente cliente = clienteService.obtenerPorId(id);
        ResponseWrapper<Cliente> response = ResponseWrapper.success(
            cliente, 
            "Cliente encontrado"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Cliente>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        log.info("Actualizando cliente ID: {}", id);
        
        Cliente cliente = clienteService.actualizar(id, request);
        ResponseWrapper<Cliente> response = ResponseWrapper.success(
            cliente, 
            "Cliente actualizado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> desactivar(@PathVariable Long id) {
        log.info("Desactivando cliente ID: {}", id);
        
        clienteService.desactivar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Cliente desactivado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<ResponseWrapper<Void>> activar(@PathVariable Long id) {
        log.info("Activando cliente ID: {}", id);
        
        clienteService.activar(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(
            null, 
            "Cliente activado exitosamente"
        );
        
        return response.toResponseEntity();
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseWrapper<List<Cliente>>> buscarPorNombre(
            @RequestParam String nombre) {
        log.info("Buscando clientes por nombre: {}", nombre);
        
        List<Cliente> clientes = clienteService.buscarPorNombre(nombre);
        ResponseWrapper<List<Cliente>> response = ResponseWrapper.success(
            clientes, 
            "Búsqueda completada"
        );
        
        return response.toResponseEntity();
    }
}