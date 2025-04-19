package com.pe.cine_cultura;

import com.pe.cine_cultura.model.Categoria;
import com.pe.cine_cultura.model.Pelicula;
import com.pe.cine_cultura.repository.CategoriaRepository;
import com.pe.cine_cultura.repository.PeliculaRepository;
import com.pe.cine_cultura.service.CategoriaService;
import com.pe.cine_cultura.service.PeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PeliculaCategoriaIntegrationTest {
    private CategoriaRepository categoriaRepository;
    private PeliculaRepository peliculaRepository;
    private CategoriaService categoriaService;
    private PeliculaService peliculaService;

    @BeforeEach
    public void setup() {
        categoriaRepository = new CategoriaRepository();
        peliculaRepository = new PeliculaRepository();

        categoriaService = new CategoriaService(categoriaRepository);
        peliculaService = new PeliculaService(peliculaRepository, categoriaService);

        Categoria accion = new Categoria();
        accion.setNombre("Acción");
        categoriaService.crearCategoria(accion);

        Categoria comedia = new Categoria();
        comedia.setNombre("Comedia");
        categoriaService.crearCategoria(comedia);
    }

    @Test
    public void testCrearYBuscarPeliculaPorCategoria() {
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        assertEquals(2, categorias.size());

        Long idCategoria = categorias.get(0).getIdCategoria();

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Matrix");
        pelicula.setDescripcion("Película de ciencia ficción");
        pelicula.setLink("http://ejemplo.com/matrix");
        pelicula.setImagen("matrix.jpg");
        pelicula.setAnioPub(1999);
        pelicula.setIdCategoria(idCategoria);
        pelicula.setCalificacion(4.8);

        Pelicula peliculaGuardada = peliculaService.crearPelicula(pelicula);
        assertNotNull(peliculaGuardada.getIdPelicula());

        Pelicula otraPelicula = new Pelicula();
        otraPelicula.setTitulo("John Wick");
        otraPelicula.setDescripcion("Película de acción");
        otraPelicula.setLink("http://ejemplo.com/johnwick");
        otraPelicula.setImagen("johnwick.jpg");
        otraPelicula.setAnioPub(2014);
        otraPelicula.setIdCategoria(idCategoria);
        otraPelicula.setCalificacion(4.5);

        peliculaService.crearPelicula(otraPelicula);

        List<Pelicula> peliculasPorCategoria = peliculaService.obtenerPeliculasPorCategoria(idCategoria);
        assertEquals(2, peliculasPorCategoria.size());

        Optional<Pelicula> peliculaRecuperada = peliculaService.obtenerPeliculaPorId(peliculaGuardada.getIdPelicula());
        assertTrue(peliculaRecuperada.isPresent());
        assertEquals("Matrix", peliculaRecuperada.get().getTitulo());

        List<Pelicula> peliculasPorTitulo = peliculaService.buscarPeliculasPorTitulo("Matrix");
        assertEquals(1, peliculasPorTitulo.size());
    }

    @Test
    public void testActualizarCategoria() {
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        Long idCategoria = categorias.get(0).getIdCategoria();

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Interstellar");
        pelicula.setDescripcion("Película de ciencia ficción");
        pelicula.setLink("http://ejemplo.com/interstellar");
        pelicula.setImagen("interstellar.jpg");
        pelicula.setAnioPub(2014);
        pelicula.setIdCategoria(idCategoria);
        pelicula.setCalificacion(4.9);

        peliculaService.crearPelicula(pelicula);

        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Ciencia Ficción");
        categoriaActualizada.setEstado(Categoria.EstadoCategoria.Activo);

        Categoria resultado = categoriaService.actualizarCategoria(idCategoria, categoriaActualizada);
        assertNotNull(resultado);
        assertEquals("Ciencia Ficción", resultado.getNombre());

        List<Pelicula> peliculasPorCategoria = peliculaService.obtenerPeliculasPorCategoria(idCategoria);
        assertEquals(1, peliculasPorCategoria.size());
        assertEquals("Interstellar", peliculasPorCategoria.get(0).getTitulo());
    }

    @Test
    public void testErrorAlCrearPeliculaConCategoriaInexistente() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Película con categoría inexistente");
        pelicula.setDescripcion("Descripción");
        pelicula.setLink("http://ejemplo.com");
        pelicula.setImagen("imagen.jpg");
        pelicula.setAnioPub(2022);
        pelicula.setIdCategoria(999L);
        pelicula.setCalificacion(4.0);

        assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.crearPelicula(pelicula);
        });
    }
}
