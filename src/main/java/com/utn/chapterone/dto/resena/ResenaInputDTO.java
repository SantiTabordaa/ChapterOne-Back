package com.utn.chapterone.dto.resena;

import java.time.LocalDate;

public class ResenaInputDTO {

    private Integer idLibro;
    private Integer idLectura;
    private String contenido;
    private Double puntaje;
    private LocalDate fechaResena;

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdLectura() {
        return idLectura;
    }

    public void setIdLectura(Integer idLectura) {
        this.idLectura = idLectura;
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
}
