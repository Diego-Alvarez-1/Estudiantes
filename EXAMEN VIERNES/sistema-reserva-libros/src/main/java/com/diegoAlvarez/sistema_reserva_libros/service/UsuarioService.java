package com.diegoAlvarez.sistema_reserva_libros.service;

import com.diegoAlvarez.sistema_reserva_libros.dto.UsuarioDTO;
import com.diegoAlvarez.sistema_reserva_libros.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(UsuarioDTO usuarioDTO);
    List<Usuario> listarUsuarios();
    Usuario obtenerUsuarioPorId(Long id);
}
