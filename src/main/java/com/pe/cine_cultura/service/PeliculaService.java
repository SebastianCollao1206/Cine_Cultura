package com.pe.cine_cultura.service;

import com.pe.cine_cultura.model.Pelicula;
import com.pe.cine_cultura.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final CategoriaService categoriaService;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository, CategoriaService categoriaService) {
        this.peliculaRepository = peliculaRepository;
        this.categoriaService = categoriaService;
    }

    public Pelicula crearPelicula(Pelicula pelicula) {
        // Verificar que la categoría exista
        if (!categoriaService.existeCategoria(pelicula.getIdCategoria())) {
            throw new IllegalArgumentException("La categoría con ID " + pelicula.getIdCategoria() + " no existe");
        }
        return peliculaRepository.guardar(pelicula);
    }

    public List<Pelicula> obtenerTodasPeliculas() {
        return peliculaRepository.listarTodas();
    }

    public Optional<Pelicula> obtenerPeliculaPorId(Long id) {
        return peliculaRepository.buscarPorId(id);
    }

    public List<Pelicula> obtenerPeliculasPorCategoria(Long idCategoria) {
        if (!categoriaService.existeCategoria(idCategoria)) {
            throw new IllegalArgumentException("La categoría con ID " + idCategoria + " no existe");
        }
        return peliculaRepository.buscarPorCategoria(idCategoria);
    }

    public List<Pelicula> obtenerPeliculasPorAnio(Integer anio) {
        return peliculaRepository.buscarPorAnio(anio);
    }

    public List<Pelicula> buscarPeliculasPorTitulo(String titulo) {
        return peliculaRepository.buscarPorTitulo(titulo);
    }

    public Pelicula actualizarPelicula(Long id, Pelicula pelicula) {
        Optional<Pelicula> peliculaExistente = peliculaRepository.buscarPorId(id);

        if (peliculaExistente.isPresent()) {
            if (!categoriaService.existeCategoria(pelicula.getIdCategoria())) {
                throw new IllegalArgumentException("La categoría con ID " + pelicula.getIdCategoria() + " no existe");
            }

            pelicula.setIdPelicula(id);
            return peliculaRepository.guardar(pelicula);
        }

        return null;
    }

    public Pelicula actualizarParcialPelicula(Long id, Pelicula datosActualizados) {
        Optional<Pelicula> peliculaExistenteOpt = peliculaRepository.buscarPorId(id);

        if (peliculaExistenteOpt.isPresent()) {
            Pelicula peliculaExistente = peliculaExistenteOpt.get();

            if (datosActualizados.getTitulo() != null) {
                peliculaExistente.setTitulo(datosActualizados.getTitulo());
            }
            if (datosActualizados.getDescripcion() != null) {
                peliculaExistente.setDescripcion(datosActualizados.getDescripcion());
            }
            if (datosActualizados.getLink() != null) {
                peliculaExistente.setLink(datosActualizados.getLink());
            }
            if (datosActualizados.getImagen() != null) {
                peliculaExistente.setImagen(datosActualizados.getImagen());
            }
            if (datosActualizados.getAnioPub() != null) {
                peliculaExistente.setAnioPub(datosActualizados.getAnioPub());
            }
            if (datosActualizados.getIdCategoria() != null) {
                if (!categoriaService.existeCategoria(datosActualizados.getIdCategoria())) {
                    throw new IllegalArgumentException("La categoría con ID " + datosActualizados.getIdCategoria() + " no existe");
                }
                peliculaExistente.setIdCategoria(datosActualizados.getIdCategoria());
            }
            if (datosActualizados.getCalificacion() != null) {
                peliculaExistente.setCalificacion(datosActualizados.getCalificacion());
            }

            return peliculaRepository.guardar(peliculaExistente);
        }

        return null;
    }

    public boolean eliminarPelicula(Long id) {
        return peliculaRepository.eliminar(id);
    }
}
