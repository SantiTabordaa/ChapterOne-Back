package com.utn.chapterone.dto.autor;

public class LibroAutorDto {
    private Integer idLibro;
    private String titulo;
    private Integer nroTomo;

    public LibroAutorDto() {
        super();
    }

    public LibroAutorDto(Integer idLibro, String titulo, Integer nroTomo) {
        super();
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.nroTomo = nroTomo;
    }

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

    public Integer getNroTomo() {
        return nroTomo;
    }

    public void setNroTomo(Integer nroTomo) {
        this.nroTomo = nroTomo;
    }
}