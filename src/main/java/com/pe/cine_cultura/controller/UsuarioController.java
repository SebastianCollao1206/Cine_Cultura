package com.pe.cine_cultura.controller;

import com.pe.cine_cultura.model.Usuario;
import com.pe.cine_cultura.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> listarUsuariosPorEstado(@PathVariable Usuario.EstadoUsuario estado) {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarUsuariosPorNombre(nombre));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            if (usuarioActualizado != null) {
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/parcial/{id}")
    public ResponseEntity<?> actualizarParcialUsuario(@PathVariable Long id, @RequestBody Usuario datosActualizados) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarParcialUsuario(id, datosActualizados);

            if (usuarioActualizado != null) {
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
