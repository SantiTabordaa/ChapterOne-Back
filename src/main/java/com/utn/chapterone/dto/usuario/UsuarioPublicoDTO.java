package com.utn.chapterone.dto.usuario;

import com.utn.chapterone.entities.Usuario;

public class UsuarioPublicoDTO {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String urlFotoPerfil;
    private String rol;

    public UsuarioPublicoDTO() {}

    public UsuarioPublicoDTO(Usuario usuario) {
        if (usuario != null) {
            this.idUsuario = usuario.getIdUsuario();
            this.nombre = usuario.getNombre();
            this.apellido = usuario.getApellido();
            this.email = usuario.getEmail();
            this.username = usuario.getUsername();
            this.urlFotoPerfil = usuario.getUrlFotoPerfil();
            this.rol = usuario.getRol();
        }
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
