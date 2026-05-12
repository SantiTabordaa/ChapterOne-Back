package com.utn.chapterone.dto.autor;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AutorListadoDto {
	@JsonProperty("id_autor")
	private Integer idAutor;
	private String nombre;
	private String apellido;
	private String pseudonimo;
	private String nacionalidad;
	@JsonProperty("url_foto")
	private String urlFoto;
	/* 
	@JsonProperty("books_top_5")
	private List<LibroTop5Dto> booksTop5;
	 */

	public AutorListadoDto() {
		super();
	}

	public AutorListadoDto(
			Integer idAutor,
			String nombre,
			String apellido,
			String pseudonimo,
			String nacionalidad,
			String urlFoto
			// List<LibroTop5Dto> booksTop5 para el listado no van los 5 mejores libros
	) {
		super();
		this.idAutor = idAutor;
		this.nombre = nombre;
		this.apellido = apellido;
		this.pseudonimo = pseudonimo;
		this.nacionalidad = nacionalidad;
		this.urlFoto = urlFoto;
		// this.booksTop5 = booksTop5;
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

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/* 
	public List<LibroTop5Dto> getBooksTop5() {
		return booksTop5;
	}

	public void setBooksTop5(List<LibroTop5Dto> booksTop5) {
		this.booksTop5 = booksTop5;
	} */
}
