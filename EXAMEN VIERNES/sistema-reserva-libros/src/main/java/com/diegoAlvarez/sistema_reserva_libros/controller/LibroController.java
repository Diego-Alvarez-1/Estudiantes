package com.diegoAlvarez.sistema_reserva_libros.controller;

import com.diegoAlvarez.sistema_reserva_libros.dto.LibroDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Libro;
import com.diegoAlvarez.sistema_reserva_libros.service.LibroService;
import com.diegoAlvarez.sistema_reserva_libros.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Libro>>> listarLibros() {
        try {
            List<Libro> libros = libroService.listarLibros();
            return ApiResponse.success(libros, "Libros obtenidos exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al listar libros: {}", e.getMessage(), e);
            return ApiResponse.<List<Libro>>error("Error al obtener los libros").toResponseEntity();
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Libro>> crearLibro(@Valid @RequestBody LibroDTO libroDTO) {
        try {
            Libro libro = libroService.crearLibro(libroDTO);
            return ApiResponse.success(libro, "Libro creado exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al crear libro: {}", e.getMessage(), e);
            return ApiResponse.<Libro>error(e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<Libro>>> listarLibrosDisponibles() {
        try {
            List<Libro> libros = libroService.listarLibrosDisponibles();
            return ApiResponse.success(libros, "Libros disponibles obtenidos exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al listar libros disponibles: {}", e.getMessage(), e);
            return ApiResponse.<List<Libro>>error("Error al obtener los libros disponibles").toResponseEntity();
        }
    }
}