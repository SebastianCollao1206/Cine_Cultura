package com.pe.cine_cultura.controller;

import com.pe.cine_cultura.model.Pelicula;
import com.pe.cine_cultura.service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final PeliculaService peliculaService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPelicula(@Valid @RequestBody Pelicula pelicula) {
        try {
            Pelicula nuevaPelicula = peliculaService.crearPelicula(pelicula);
            return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliculaService.obtenerTodasPeliculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPeliculaPorId(@PathVariable Long id) {
        return peliculaService.obtenerPeliculaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> listarPeliculasPorCategoria(@PathVariable Long idCategoria) {
        try {
            List<Pelicula> peliculas = peliculaService.obtenerPeliculasPorCategoria(idCategoria);
            return ResponseEntity.ok(peliculas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<Pelicula>> listarPeliculasPorAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(peliculaService.obtenerPeliculasPorAnio(anio));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Pelicula>> buscarPeliculasPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(peliculaService.buscarPeliculasPorTitulo(titulo));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPelicula(@PathVariable Long id, @Valid @RequestBody Pelicula pelicula) {
        try {
            Pelicula peliculaActualizada = peliculaService.actualizarPelicula(id, pelicula);
            if (peliculaActualizada != null) {
                return ResponseEntity.ok(peliculaActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/parcial/{id}")
    public ResponseEntity<?> actualizarParcialPelicula(@PathVariable Long id, @RequestBody Pelicula datosActualizados) {
        try {
            Pelicula peliculaActualizada = peliculaService.actualizarParcialPelicula(id, datosActualizados);

            if (peliculaActualizada != null) {
                return ResponseEntity.ok(peliculaActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        if (peliculaService.eliminarPelicula(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
