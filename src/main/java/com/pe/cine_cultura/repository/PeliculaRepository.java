package com.pe.cine_cultura.repository;

import com.pe.cine_cultura.model.Pelicula;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PeliculaRepository {
    private final Map<Long, Pelicula> peliculas = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public Pelicula guardar(Pelicula pelicula) {
        if (pelicula.getIdPelicula() == null) {
            Long id = secuencia.getAndIncrement();
            pelicula.setIdPelicula(id);
            peliculas.put(id, pelicula);
        } else {
            peliculas.put(pelicula.getIdPelicula(), pelicula);
        }
        return pelicula;
    }

    public List<Pelicula> listarTodas() {
        return new ArrayList<>(peliculas.values());
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        return Optional.ofNullable(peliculas.get(id));
    }

    public List<Pelicula> buscarPorCategoria(Long idCategoria) {
        return peliculas.values().stream()
                .filter(p -> p.getIdCategoria().equals(idCategoria))
                .collect(Collectors.toList());
    }

    public List<Pelicula> buscarPorAnio(Integer anio) {
        return peliculas.values().stream()
                .filter(p -> p.getAnioPub().equals(anio))
                .collect(Collectors.toList());
    }

    public boolean eliminar(Long id) {
        return peliculas.remove(id) != null;
    }

    public List<Pelicula> buscarPorTitulo(String titulo) {
        return peliculas.values().stream()
                .filter(p -> p.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
}
