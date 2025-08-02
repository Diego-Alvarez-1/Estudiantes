package com.diegoAlvarez.sistema_reserva_libros.controller;

import com.diegoAlvarez.sistema_reserva_libros.dto.ReservaDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Reserva;
import com.diegoAlvarez.sistema_reserva_libros.service.ReservaService;
import com.diegoAlvarez.sistema_reserva_libros.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Reserva>> reservarLibro(@Valid @RequestBody ReservaDTO reservaDTO) {
        try {
            Reserva reserva = reservaService.crearReserva(reservaDTO);
            return ApiResponse.success(reserva, "Reserva creada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al crear reserva: {}", e.getMessage(), e);
            return ApiResponse.<Reserva>error(e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse<List<Reserva>>> verReservasDeUsuario(@PathVariable Long id) {
        try {
            List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(id);
            String mensaje = reservas.isEmpty() ? 
                "El usuario no tiene reservas" : 
                "Reservas del usuario obtenidas exitosamente";
            return ApiResponse.success(reservas, mensaje).toResponseEntity();
        } catch (Exception e) {
            log.error("Error al obtener reservas del usuario: {}", e.getMessage(), e);
            return ApiResponse.<List<Reserva>>error("Error al obtener las reservas del usuario").toResponseEntity();
        }
    }
}