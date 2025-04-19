package com.pe.cine_cultura;

import com.pe.cine_cultura.model.Usuario;
import com.pe.cine_cultura.repository.UsuarioRepository;
import com.pe.cine_cultura.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(1L, "Juan Perez", "juan@example.com", "password", Usuario.EstadoUsuario.Activo);
    }

    @Test
    public void testCrearUsuario() {
        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);

        assertNotNull(usuarioCreado);
        assertEquals("Juan Perez", usuarioCreado.getNombre());
        verify(usuarioRepository, times(1)).buscarPorEmail(anyString());
        verify(usuarioRepository, times(1)).guardar(any(Usuario.class));
    }

    @Test
    public void testCrearUsuarioEmailExistente() {
        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.crearUsuario(usuario);
        });

        verify(usuarioRepository, times(1)).buscarPorEmail(anyString());
        verify(usuarioRepository, never()).guardar(any(Usuario.class));
    }

    @Test
    public void testObtenerTodosUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Juan Perez", "juan@example.com", "password", Usuario.EstadoUsuario.Activo),
                new Usuario(2L, "Ana Lopez", "ana@example.com", "password", Usuario.EstadoUsuario.Activo)
        );

        when(usuarioRepository.listarTodos()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.obtenerTodosUsuarios();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).listarTodos();
    }

    @Test
    public void testObtenerUsuarioPorId() {
        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan Perez", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).buscarPorId(1L);
    }
}
