package com.utn.chapterone.dtos.libro;

import java.util.List;

public class LibroInputDTO {
    private String titulo;
    private String sinopsis;
    private Integer nroTomo;
    private Integer cantPag;
    private String saga; // Se mantiene por nombre
    private List<Integer> autoresIds;
    private List<Integer> generosIds;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Integer getNroTomo() {
        return nroTomo;
    }

    public void setNroTomo(Integer nroTomo) {
        this.nroTomo = nroTomo;
    }

    public Integer getCantPag() {
        return cantPag;
    }

    public void setCantPag(Integer cantPag) {
        this.cantPag = cantPag;
    }

    public String getSaga() {
        return saga;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public List<Integer> getAutoresIds() {
        return autoresIds;
    }

    public void setAutoresIds(List<Integer> autoresIds) {
        this.autoresIds = autoresIds;
    }

    public List<Integer> getGenerosIds() {
        return generosIds;
    }

    public void setGenerosIds(List<Integer> generosIds) {
        this.generosIds = generosIds;
    }
}
