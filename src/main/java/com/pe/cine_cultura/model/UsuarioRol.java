package com.pe.cine_cultura.model;

import jakarta.validation.constraints.NotNull;

public class UsuarioRol {
    private Long idUsuarioRol;

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El rol es obligatorio")
    private Long idRol;

    public UsuarioRol() {
    }

    public UsuarioRol(Long idUsuarioRol, Long idUsuario, Long idRol) {
        this.idUsuarioRol = idUsuarioRol;
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public Long getIdUsuarioRol() {
        return idUsuarioRol;
    }

    public void setIdUsuarioRol(Long idUsuarioRol) {
        this.idUsuarioRol = idUsuarioRol;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}
