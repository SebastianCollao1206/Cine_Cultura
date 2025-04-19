package com.pe.cine_cultura.model;

import jakarta.validation.constraints.*;

public class Pelicula {
    private Long idPelicula;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El link es obligatorio")
    private String link;

    @NotBlank(message = "La imagen es obligatoria")
    private String imagen;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1895, message = "El año debe ser posterior a 1895")
    private Integer anioPub;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Double calificacion;

    public Pelicula() {
    }

    public Pelicula(Long idPelicula, String titulo, String descripcion, String link, String imagen, Integer anioPub, Long idCategoria, Double calificacion) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
        this.imagen = imagen;
        this.anioPub = anioPub;
        this.idCategoria = idCategoria;
        this.calificacion = calificacion;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getAnioPub() {
        return anioPub;
    }

    public void setAnioPub(Integer anioPub) {
        this.anioPub = anioPub;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
