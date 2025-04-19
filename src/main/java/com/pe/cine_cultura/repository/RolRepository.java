package com.pe.cine_cultura.repository;

import com.pe.cine_cultura.model.Rol;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class RolRepository {
    private final Map<Long, Rol> roles = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public Rol guardar(Rol rol) {
        if (rol.getIdRol() == null) {
            Long id = secuencia.getAndIncrement();
            rol.setIdRol(id);
            roles.put(id, rol);
        } else {
            roles.put(rol.getIdRol(), rol);
        }
        return rol;
    }

    public List<Rol> listarTodos() {
        return new ArrayList<>(roles.values());
    }

    public Optional<Rol> buscarPorId(Long id) {
        return Optional.ofNullable(roles.get(id));
    }

    public boolean eliminar(Long id) {
        return roles.remove(id) != null;
    }

    public boolean existeRol(Long id) {
        return roles.containsKey(id);
    }

    public List<Rol> buscarPorNombre(String nombre) {
        return roles.values().stream()
                .filter(r -> r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
