package com.utn.chapterone.entities;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "autores")
public class Autor {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAutor;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	private String pseudonimo;
	private String nacionalidad;
	private LocalDate fechaNacimiento;
	private LocalDate fechaFallecimiento;
	@Column(length = 2000)
	private String resumen;
	@Column(name = "lugar_nacimiento")
	private String lugarNacimiento;
	@Column(name = "lugar_fallecimiento")
	private String lugarFallecimiento;
	private String urlFoto;
	@ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
	private List<Libro> libros;

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

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public Autor() {
		super();
	}
	public Autor(String nombre, String apellido, String pseudonimo, String nacionalidad, String urlFoto) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.pseudonimo = pseudonimo;
		this.nacionalidad = nacionalidad;
		this.urlFoto = urlFoto;
	}
	

}
