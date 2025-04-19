package com.pe.cine_cultura.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Usuario {
    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private EstadoUsuario estado;

    public enum EstadoUsuario {
        Activo, Inactivo
    }

    public Usuario() {
        this.estado = EstadoUsuario.Activo;
    }

    public Usuario(Long idUsuario, String nombre, String email, String password, EstadoUsuario estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.estado = estado != null ? estado : EstadoUsuario.Activo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}
