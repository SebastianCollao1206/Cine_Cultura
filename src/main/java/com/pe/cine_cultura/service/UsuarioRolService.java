package com.pe.cine_cultura.service;

import com.pe.cine_cultura.model.UsuarioRol;
import com.pe.cine_cultura.repository.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioRolService {
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioService usuarioService;
    private final RolService rolService;

    @Autowired
    public UsuarioRolService(UsuarioRolRepository usuarioRolRepository, UsuarioService usuarioService, RolService rolService) {
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    public UsuarioRol asignarRolAUsuario(Long idUsuario, Long idRol) {
        if (!usuarioService.existeUsuario(idUsuario)) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe");
        }

        if (!rolService.existeRol(idRol)) {
            throw new IllegalArgumentException("El rol con ID " + idRol + " no existe");
        }

        if (usuarioRolRepository.existeRelacion(idUsuario, idRol)) {
            throw new IllegalArgumentException("El usuario ya tiene asignado ese rol");
        }

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setIdUsuario(idUsuario);
        usuarioRol.setIdRol(idRol);

        return usuarioRolRepository.guardar(usuarioRol);
    }

    public List<UsuarioRol> obtenerTodosUsuariosRoles() {
        return usuarioRolRepository.listarTodos();
    }

    public List<UsuarioRol> obtenerUsuariosPorRol(Long idRol) {
        if (!rolService.existeRol(idRol)) {
            throw new IllegalArgumentException("El rol con ID " + idRol + " no existe");
        }

        return usuarioRolRepository.buscarPorIdRol(idRol);
    }

    public boolean eliminarUsuarioRol(Long id) {
        return usuarioRolRepository.eliminar(id);
    }
}
