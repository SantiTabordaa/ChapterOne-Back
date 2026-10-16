package com.utn.chapterone.dto.lectura;

import java.util.Date;

public class LecturaInputDTO {

    private Integer idLibro;
    private LibroRef libro;
    private Integer idUsuario;
    private String estado;
    private Date fechaIni;
    private Date fechaFin;

    public static class LibroRef {
        private Integer idLibro;

        public Integer getIdLibro() {
            return idLibro;
        }

        public void setIdLibro(Integer idLibro) {
            this.idLibro = idLibro;
        }
    }

    public Integer resolveIdLibro() {
        if (idLibro != null) {
            return idLibro;
        }
        if (libro != null) {
            return libro.getIdLibro();
        }
        return null;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public LibroRef getLibro() {
        return libro;
    }

    public void setLibro(LibroRef libro) {
        this.libro = libro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
}
