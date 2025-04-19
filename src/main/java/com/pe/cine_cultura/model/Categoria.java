package com.pe.cine_cultura.model;

import jakarta.validation.constraints.NotBlank;

public class Categoria {
    private Long idCategoria;

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombre;
    private EstadoCategoria estado;

    public enum EstadoCategoria {
        Activo, Inactivo
    }

    public Categoria() {
        this.estado = EstadoCategoria.Activo;
    }

    public Categoria(Long idCategoria, String nombre, EstadoCategoria estado) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.estado = estado != null ? estado : EstadoCategoria.Activo;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoCategoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoCategoria estado) {
        this.estado = estado;
    }
}
