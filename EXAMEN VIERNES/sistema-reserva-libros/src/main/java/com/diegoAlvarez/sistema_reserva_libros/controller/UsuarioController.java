package com.diegoAlvarez.sistema_reserva_libros.controller;

import com.diegoAlvarez.sistema_reserva_libros.dto.UsuarioDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Usuario;
import com.diegoAlvarez.sistema_reserva_libros.service.UsuarioService;
import com.diegoAlvarez.sistema_reserva_libros.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.crearUsuario(usuarioDTO);
            return ApiResponse.success(usuario, "Usuario creado exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage(), e);
            return ApiResponse.<Usuario>error(e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            return ApiResponse.success(usuarios, "Usuarios obtenidos exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error("Error al listar usuarios: {}", e.getMessage(), e);
            return ApiResponse.<List<Usuario>>error("Error al obtener los usuarios").toResponseEntity();
        }
    }
}
