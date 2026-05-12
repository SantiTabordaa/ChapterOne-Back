package com.utn.chapterone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibroTop5Dto {
	@JsonProperty("id_libro")
	private Integer idLibro;
	private String titulo;
	@JsonProperty("cover_url")
	private String coverUrl;

	public LibroTop5Dto() {
		super();
	}

	public LibroTop5Dto(Integer idLibro, String titulo, String coverUrl) {
		super();
		this.idLibro = idLibro;
		this.titulo = titulo;
		this.coverUrl = coverUrl;
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

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
}
