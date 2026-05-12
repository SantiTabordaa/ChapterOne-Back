package com.utn.chapterone.dto.Libro;

import java.util.List;

public class LibroInputDTO {
    private String titulo;
    private String sinopsis;
    private Integer nroTomo;
    private Integer cantPag;
    private String saga;
    private List<String> autores;
    private List<String> generos;

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

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }
}
