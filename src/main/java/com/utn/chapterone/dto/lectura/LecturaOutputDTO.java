package com.utn.chapterone.dto.lectura;

import com.utn.chapterone.dto.Libro.LibroOutputDTO;
import com.utn.chapterone.entities.Resena;

import java.util.Date;

public class LecturaOutputDTO {

    private Integer idLectura;
    private Integer idUsuario;
    private String username;
    private LibroOutputDTO libro;
    private String estado;
    private Date fechaIni;
    private Date fechaFin;
    private Resena resena;

    public Integer getIdLectura() {
        return idLectura;
    }

    public void setIdLectura(Integer idLectura) {
        this.idLectura = idLectura;
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

    public LibroOutputDTO getLibro() {
        return libro;
    }

    public void setLibro(LibroOutputDTO libro) {
        this.libro = libro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Resena getResena() {
        return resena;
    }

    public void setResena(Resena resena) {
        this.resena = resena;
    }
}
