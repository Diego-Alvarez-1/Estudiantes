package org.ebiz.msreservassalas.controller;

import lombok.extern.slf4j.Slf4j;
import org.ebiz.msreservassalas.model.Reserva;
import org.ebiz.msreservassalas.service.ReservaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaServiceInterface reservaService;

    public ReservaController(ReservaServiceInterface reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
        try {
            Reserva guardada = reservaService.crearReserva(reserva);
            return ResponseWrapper.success(guardada, "Reserva creada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listar();
    }

    @GetMapping("/{id}")
    public Reserva obtenerPorId(@PathVariable Long id) {
        try {
            return reservaService.obtenerPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Reserva> listarPorEstudiante(@PathVariable Long estudianteId) {
        return reservaService.listarPorEstudiante(estudianteId);
    }

    @GetMapping("/sala/{salaId}")
    public List<Reserva> listarPorSala(@PathVariable Long salaId) {
        return reservaService.listarPorSala(salaId);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            return ResponseWrapper.success(null, "Reserva cancelada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }
}