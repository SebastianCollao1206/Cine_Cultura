package com.pe.cine_cultura.controller;

import com.pe.cine_cultura.model.UsuarioRol;
import com.pe.cine_cultura.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios-roles")
public class UsuarioRolController {
    private final UsuarioRolService usuarioRolService;

    @Autowired
    public UsuarioRolController(UsuarioRolService usuarioRolService) {
        this.usuarioRolService = usuarioRolService;
    }

    @PostMapping("/asignar")
    public ResponseEntity<?> asignarRolAUsuario(@RequestBody Map<String, Long> datos) {
        try {
            Long idUsuario = datos.get("idUsuario");
            Long idRol = datos.get("idRol");

            UsuarioRol usuarioRol = usuarioRolService.asignarRolAUsuario(idUsuario, idRol);
            return new ResponseEntity<>(usuarioRol, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioRol>> listarUsuariosRoles() {
        return ResponseEntity.ok(usuarioRolService.obtenerTodosUsuariosRoles());
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<?> listarUsuariosPorRol(@PathVariable Long idRol) {
        try {
            List<UsuarioRol> usuarios = usuarioRolService.obtenerUsuariosPorRol(idRol);
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuarioRol(@PathVariable Long id) {
        if (usuarioRolService.eliminarUsuarioRol(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
