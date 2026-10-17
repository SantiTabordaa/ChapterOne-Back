package com.utn.chapterone.dto.resena;

import java.time.LocalDate;

public class ResenaOutputDTO {

    private Integer idResena;
    private String contenido;
    private Double puntaje;
    private LocalDate fechaResena;
    private Integer idLectura;
    private Integer idLibro;
    private String tituloLibro;
    private Integer idUsuario;
    private String username;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String urlFotoPerfil;

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public LocalDate getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDate fechaResena) {
        this.fechaResena = fechaResena;
    }

    public Integer getIdLectura() {
        return idLectura;
    }

    public void setIdLectura(Integer idLectura) {
        this.idLectura = idLectura;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
}
