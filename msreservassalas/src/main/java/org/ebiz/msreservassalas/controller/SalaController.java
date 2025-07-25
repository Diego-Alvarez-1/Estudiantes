package org.ebiz.msreservassalas.controller;

import lombok.extern.slf4j.Slf4j;
import org.ebiz.msreservassalas.model.Sala;
import org.ebiz.msreservassalas.service.SalaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    private final SalaServiceInterface salaService;

    public SalaController(SalaServiceInterface salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Sala sala) {
        try {
            Sala guardada = salaService.guardar(sala);
            return ResponseWrapper.success(guardada, "Sala guardada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping
    public List<Sala> listar() {
        return salaService.listar();
    }

    @GetMapping("/{id}")
    public Sala obtenerPorId(@PathVariable Long id) {
        try {
            return salaService.obtenerPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Sala sala) {
        try {
            Sala actualizada = salaService.actualizar(id, sala);
            return ResponseWrapper.success(actualizada, "Sala actualizada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            salaService.eliminar(id);
            return ResponseWrapper.success(null, "Sala eliminada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam boolean habilitado) {
        try {
            salaService.cambiarEstado(id, habilitado);
            return ResponseWrapper.success(null, "Estado cambiado exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }
}