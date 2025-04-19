package com.pe.cine_cultura.controller;

import com.pe.cine_cultura.model.Rol;
import com.pe.cine_cultura.service.RolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody Rol rol) {
        Rol nuevoRol = rolService.crearRol(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.obtenerTodosRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        return rolService.obtenerRolPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Rol>> buscarRolesPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(rolService.buscarRolesPorNombre(nombre));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @Valid @RequestBody Rol rol) {
        Rol rolActualizado = rolService.actualizarRol(id, rol);
        if (rolActualizado != null) {
            return ResponseEntity.ok(rolActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        if (rolService.eliminarRol(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
