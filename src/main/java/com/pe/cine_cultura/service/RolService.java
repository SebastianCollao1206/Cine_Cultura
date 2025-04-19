package com.pe.cine_cultura.service;

import com.pe.cine_cultura.model.Rol;
import com.pe.cine_cultura.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol crearRol(Rol rol) {
        return rolRepository.guardar(rol);
    }

    public List<Rol> obtenerTodosRoles() {
        return rolRepository.listarTodos();
    }

    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepository.buscarPorId(id);
    }

    public Rol actualizarRol(Long id, Rol rol) {
        Optional<Rol> rolExistente = rolRepository.buscarPorId(id);

        if (rolExistente.isPresent()) {
            rol.setIdRol(id);
            return rolRepository.guardar(rol);
        }

        return null;
    }

    public boolean eliminarRol(Long id) {
        return rolRepository.eliminar(id);
    }

    public boolean existeRol(Long id) {
        return rolRepository.existeRol(id);
    }

    public List<Rol> buscarRolesPorNombre(String nombre) {
        return rolRepository.buscarPorNombre(nombre);
    }
}
