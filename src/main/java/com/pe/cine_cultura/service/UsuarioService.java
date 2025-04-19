package com.pe.cine_cultura.service;

import com.pe.cine_cultura.model.Usuario;
import com.pe.cine_cultura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        return usuarioRepository.guardar(usuario);
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> obtenerUsuariosPorEstado(Usuario.EstadoUsuario estado) {
        return usuarioRepository.buscarPorEstado(estado);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorId(id);

        if (usuarioExistente.isPresent()) {
            Optional<Usuario> usuarioConEmail = usuarioRepository.buscarPorEmail(usuario.getEmail());
            if (usuarioConEmail.isPresent() && !usuarioConEmail.get().getIdUsuario().equals(id)) {
                throw new IllegalArgumentException("El email ya está siendo usado por otro usuario");
            }

            usuario.setIdUsuario(id);
            return usuarioRepository.guardar(usuario);
        }

        return null;
    }

    public Usuario actualizarParcialUsuario(Long id, Usuario datosActualizados) {
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.buscarPorId(id);

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();

            if (datosActualizados.getNombre() != null) {
                usuarioExistente.setNombre(datosActualizados.getNombre());
            }
            if (datosActualizados.getEmail() != null) {
                Optional<Usuario> usuarioConEmail = usuarioRepository.buscarPorEmail(datosActualizados.getEmail());
                if (usuarioConEmail.isPresent() && !usuarioConEmail.get().getIdUsuario().equals(id)) {
                    throw new IllegalArgumentException("El email ya está siendo usado por otro usuario");
                }
                usuarioExistente.setEmail(datosActualizados.getEmail());
            }
            if (datosActualizados.getPassword() != null) {
                usuarioExistente.setPassword(datosActualizados.getPassword());
            }
            if (datosActualizados.getEstado() != null) {
                usuarioExistente.setEstado(datosActualizados.getEstado());
            }

            return usuarioRepository.guardar(usuarioExistente);
        }

        return null;
    }

    public boolean eliminarUsuario(Long id) {
        return usuarioRepository.eliminar(id);
    }

    public boolean existeUsuario(Long id) {
        return usuarioRepository.existeUsuario(id);
    }

    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        return usuarioRepository.buscarPorNombre(nombre);
    }
}
