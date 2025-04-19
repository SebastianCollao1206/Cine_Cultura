package com.pe.cine_cultura.model;

import jakarta.validation.constraints.NotBlank;

public class Rol {
    private Long idRol;

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    public Rol() {
    }

    public Rol(Long idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
