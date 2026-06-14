package com.utn.chapterone.dto.Libro;

import java.util.List;

import com.utn.chapterone.dto.autor.AutorDTO;

public class LibroOutputDTO {
    private Integer idLibro;
    private String titulo;
    private String sinopsis;
    private Integer nroTomo;
    private Integer cantPag;
    private Double valoracion;
    private String saga;
    private String urlPortada;
    private List<AutorDTO> autores;
    private List<String> generos;

    // Getters y Setters
    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrlPortada() {
        return urlPortada;
    }
    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
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

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public String getSaga() {
        return saga;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }
}
