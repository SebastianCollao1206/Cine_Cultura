package com.pe.cine_cultura.repository;

import com.pe.cine_cultura.model.UsuarioRol;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UsuarioRolRepository {
    private final Map<Long, UsuarioRol> usuariosRoles = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public UsuarioRol guardar(UsuarioRol usuarioRol) {
        if (usuarioRol.getIdUsuarioRol() == null) {
            Long id = secuencia.getAndIncrement();
            usuarioRol.setIdUsuarioRol(id);
            usuariosRoles.put(id, usuarioRol);
        } else {
            usuariosRoles.put(usuarioRol.getIdUsuarioRol(), usuarioRol);
        }
        return usuarioRol;
    }

    public List<UsuarioRol> listarTodos() {
        return new ArrayList<>(usuariosRoles.values());
    }

    public List<UsuarioRol> buscarPorIdRol(Long idRol) {
        return usuariosRoles.values().stream()
                .filter(ur -> ur.getIdRol().equals(idRol))
                .collect(Collectors.toList());
    }

    public boolean eliminar(Long id) {
        return usuariosRoles.remove(id) != null;
    }

    public boolean existeRelacion(Long idUsuario, Long idRol) {
        return usuariosRoles.values().stream()
                .anyMatch(ur -> ur.getIdUsuario().equals(idUsuario) && ur.getIdRol().equals(idRol));
    }
}
