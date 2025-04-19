package com.pe.cine_cultura;

import com.pe.cine_cultura.model.Categoria;
import com.pe.cine_cultura.repository.CategoriaRepository;
import com.pe.cine_cultura.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Acción");

        when(categoriaRepository.guardar(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = categoriaService.crearCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("Acción", resultado.getNombre());
        verify(categoriaRepository).guardar(categoria);
    }

    @Test
    public void testObtenerTodasCategorias() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Acción", Categoria.EstadoCategoria.Activo),
                new Categoria(2L, "Comedia", Categoria.EstadoCategoria.Activo)
        );

        when(categoriaRepository.listarTodas()).thenReturn(categorias);

        List<Categoria> resultado = categoriaService.obtenerTodasCategorias();

        assertEquals(2, resultado.size());
        verify(categoriaRepository).listarTodas();
    }

    @Test
    public void testObtenerCategoriaPorId() {
        Categoria categoria = new Categoria(1L, "Acción", Categoria.EstadoCategoria.Activo);

        when(categoriaRepository.buscarPorId(1L)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultado = categoriaService.obtenerCategoriaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Acción", resultado.get().getNombre());
        verify(categoriaRepository).buscarPorId(1L);
    }

    @Test
    public void testExisteCategoria() {
        when(categoriaRepository.existeCategoria(1L)).thenReturn(true);
        when(categoriaRepository.existeCategoria(2L)).thenReturn(false);

        assertTrue(categoriaService.existeCategoria(1L));
        assertFalse(categoriaService.existeCategoria(2L));

        verify(categoriaRepository).existeCategoria(1L);
        verify(categoriaRepository).existeCategoria(2L);
    }
}
