package com.pe.cine_cultura;

import com.pe.cine_cultura.model.Pelicula;
import com.pe.cine_cultura.repository.PeliculaRepository;
import com.pe.cine_cultura.service.CategoriaService;
import com.pe.cine_cultura.service.PeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PeliculaServiceTest {
    @Mock
    private PeliculaRepository peliculaRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private PeliculaService peliculaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Avengers");
        pelicula.setDescripcion("Película de superhéroes");
        pelicula.setLink("http://ejemplo.com");
        pelicula.setImagen("imagen.jpg");
        pelicula.setAnioPub(2012);
        pelicula.setIdCategoria(1L);
        pelicula.setCalificacion(4.5);

        when(categoriaService.existeCategoria(1L)).thenReturn(true);
        when(peliculaRepository.guardar(any(Pelicula.class))).thenReturn(pelicula);

        Pelicula resultado = peliculaService.crearPelicula(pelicula);

        assertNotNull(resultado);
        assertEquals("Avengers", resultado.getTitulo());
        verify(categoriaService).existeCategoria(1L);
        verify(peliculaRepository).guardar(pelicula);
    }

    @Test
    public void testCrearPeliculaCategoriaNoExiste() {
        // Preparación
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Avengers");
        pelicula.setIdCategoria(999L);

        when(categoriaService.existeCategoria(999L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.crearPelicula(pelicula);
        });

        verify(categoriaService).existeCategoria(999L);
        verify(peliculaRepository, never()).guardar(any(Pelicula.class));
    }

    @Test
    public void testObtenerTodasPeliculas() {
        List<Pelicula> peliculas = Arrays.asList(
                crearPeliculaEjemplo(1L, "Película 1", 1L),
                crearPeliculaEjemplo(2L, "Película 2", 2L)
        );

        when(peliculaRepository.listarTodas()).thenReturn(peliculas);

        List<Pelicula> resultado = peliculaService.obtenerTodasPeliculas();

        assertEquals(2, resultado.size());
        verify(peliculaRepository).listarTodas();
    }

    @Test
    public void testObtenerPeliculasPorCategoria() {
        List<Pelicula> peliculas = Arrays.asList(
                crearPeliculaEjemplo(1L, "Película 1", 1L),
                crearPeliculaEjemplo(3L, "Película 3", 1L)
        );

        when(categoriaService.existeCategoria(1L)).thenReturn(true);
        when(peliculaRepository.buscarPorCategoria(1L)).thenReturn(peliculas);

        List<Pelicula> resultado = peliculaService.obtenerPeliculasPorCategoria(1L);

        assertEquals(2, resultado.size());
        verify(categoriaService).existeCategoria(1L);
        verify(peliculaRepository).buscarPorCategoria(1L);
    }

    @Test
    public void testBuscarPeliculasPorTitulo() {
        List<Pelicula> peliculas = Arrays.asList(
                crearPeliculaEjemplo(1L, "Avengers", 1L),
                crearPeliculaEjemplo(2L, "Avengers: Endgame", 1L)
        );

        when(peliculaRepository.buscarPorTitulo("Avengers")).thenReturn(peliculas);

        List<Pelicula> resultado = peliculaService.buscarPeliculasPorTitulo("Avengers");

        assertEquals(2, resultado.size());
        verify(peliculaRepository).buscarPorTitulo("Avengers");
    }

    private Pelicula crearPeliculaEjemplo(Long id, String titulo, Long idCategoria) {
        Pelicula pelicula = new Pelicula();
        pelicula.setIdPelicula(id);
        pelicula.setTitulo(titulo);
        pelicula.setDescripcion("Descripción");
        pelicula.setLink("http://ejemplo.com");
        pelicula.setImagen("imagen.jpg");
        pelicula.setAnioPub(2022);
        pelicula.setIdCategoria(idCategoria);
        pelicula.setCalificacion(4.0);
        return pelicula;
    }
}
