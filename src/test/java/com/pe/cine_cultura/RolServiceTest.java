package com.pe.cine_cultura;

import com.pe.cine_cultura.model.Rol;
import com.pe.cine_cultura.repository.RolRepository;
import com.pe.cine_cultura.service.RolService;
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
import static org.mockito.Mockito.*;

public class RolServiceTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        rol = new Rol(1L, "ADMIN");
    }

    @Test
    public void testCrearRol() {
        when(rolRepository.guardar(any(Rol.class))).thenReturn(rol);

        Rol rolCreado = rolService.crearRol(rol);

        assertNotNull(rolCreado);
        assertEquals("ADMIN", rolCreado.getNombre());
        verify(rolRepository, times(1)).guardar(any(Rol.class));
    }

    @Test
    public void testObtenerTodosRoles() {
        List<Rol> roles = Arrays.asList(
                new Rol(1L, "ADMIN"),
                new Rol(2L, "USER")
        );

        when(rolRepository.listarTodos()).thenReturn(roles);

        List<Rol> resultado = rolService.obtenerTodosRoles();

        assertEquals(2, resultado.size());
        verify(rolRepository, times(1)).listarTodos();
    }

    @Test
    public void testObtenerRolPorId() {
        when(rolRepository.buscarPorId(1L)).thenReturn(Optional.of(rol));

        Optional<Rol> resultado = rolService.obtenerRolPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ADMIN", resultado.get().getNombre());
        verify(rolRepository, times(1)).buscarPorId(1L);
    }

    @Test
    public void testActualizarRol() {
        when(rolRepository.buscarPorId(1L)).thenReturn(Optional.of(rol));
        when(rolRepository.guardar(any(Rol.class))).thenReturn(rol);

        Rol rolActualizado = new Rol(1L, "ADMIN_UPDATED");
        Rol resultado = rolService.actualizarRol(1L, rolActualizado);

        assertNotNull(resultado);
        verify(rolRepository, times(1)).buscarPorId(1L);
        verify(rolRepository, times(1)).guardar(any(Rol.class));
    }
}
