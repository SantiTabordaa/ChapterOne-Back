package com.utn.chapterone.dto.autor;

import java.util.List;
import java.time.LocalDate;

public class AutorDetalleDto {
    private Integer idAutor;
    private String nombre;
    private String apellido;
    private String pseudonimo;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private LocalDate fechaFallecimiento;
    private String resumen;
    private String lugarNacimiento;
    private String lugarFallecimiento;
    private String urlFoto;
    private List<LibroAutorDto> libros;

    public AutorDetalleDto() {
        super();
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
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

    public String getPseudonimo() {
        return pseudonimo;
    }

    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(LocalDate fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getLugarFallecimiento() {
        return lugarFallecimiento;
    }

    public void setLugarFallecimiento(String lugarFallecimiento) {
        this.lugarFallecimiento = lugarFallecimiento;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public List<LibroAutorDto> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroAutorDto> libros) {
        this.libros = libros;
    }
}