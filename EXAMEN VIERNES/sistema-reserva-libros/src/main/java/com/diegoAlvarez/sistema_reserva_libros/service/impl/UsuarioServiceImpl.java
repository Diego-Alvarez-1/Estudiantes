package com.diegoAlvarez.sistema_reserva_libros.service.impl;

import com.diegoAlvarez.sistema_reserva_libros.dto.UsuarioDTO;
import com.diegoAlvarez.sistema_reserva_libros.exception.EntityNotFoundException;
import com.diegoAlvarez.sistema_reserva_libros.model.Usuario;
import com.diegoAlvarez.sistema_reserva_libros.repository.UsuarioRepository;
import com.diegoAlvarez.sistema_reserva_libros.service.UsuarioService;
import com.diegoAlvarez.sistema_reserva_libros.exception.LibroYaReservadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + usuarioDTO.getCorreo());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }
}