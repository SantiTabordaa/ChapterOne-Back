package com.utn.chapterone.entities;
import jakarta.persistence.*;

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
	private String urlFoto;


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
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
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
