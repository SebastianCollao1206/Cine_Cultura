package com.pe.cine_cultura.repository;

import com.pe.cine_cultura.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepository {
    private final Map<Long, Usuario> usuarios = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public Usuario guardar(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            Long id = secuencia.getAndIncrement();
            usuario.setIdUsuario(id);
            usuarios.put(id, usuario);
        } else {
            usuarios.put(usuario.getIdUsuario(), usuario);
        }
        return usuario;
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    public List<Usuario> buscarPorEstado(Usuario.EstadoUsuario estado) {
        return usuarios.values().stream()
                .filter(u -> u.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public boolean eliminar(Long id) {
        return usuarios.remove(id) != null;
    }

    public boolean existeUsuario(Long id) {
        return usuarios.containsKey(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarios.values().stream()
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
