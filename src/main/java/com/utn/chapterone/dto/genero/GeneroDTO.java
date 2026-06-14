package com.utn.chapterone.dto.genero;

public class GeneroDTO {
    private Integer idGenero;
    private String nombreGen;

    public GeneroDTO() {}

    public GeneroDTO(Integer idGenero, String nombreGen) {
        this.idGenero = idGenero;
        this.nombreGen = nombreGen;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGen() {
        return nombreGen;
    }

    public void setNombreGen(String nombreGen) {
        this.nombreGen = nombreGen;
    }
}