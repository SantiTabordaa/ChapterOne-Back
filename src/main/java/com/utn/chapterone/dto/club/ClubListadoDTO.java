package com.utn.chapterone.dto.club;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClubListadoDTO {
	
	@JsonProperty("id_club")
	private Integer idClub;
	
	@JsonProperty("nombre_club")
	private String nombreClub;
	
	private String descripcion;
	
	private String genero;
	
	@JsonProperty("cantidad_integrantes")
	private Long cantidadIntegrantes;
	
	@JsonProperty("es_privado")
	private Boolean esPrivado;

	

	
	// Constructores
	public ClubListadoDTO() {
		super();
	}
	
	public ClubListadoDTO(
			Integer idClub,
			String nombreClub,
			String descripcion,
			String genero,
			Long cantidadIntegrantes,
			Boolean esPrivado
	) {
		super();
		this.idClub = idClub;
		this.nombreClub = nombreClub;
		this.descripcion = descripcion;
		this.genero = genero;
		this.cantidadIntegrantes = cantidadIntegrantes;
		this.esPrivado = esPrivado;
	}
	
	// Getters y Setters
	public Integer getIdClub() {
		return idClub;
	}
	
	public void setIdClub(Integer idClub) {
		this.idClub = idClub;
	}
	
	public String getNombreClub() {
		return nombreClub;
	}
	
	public void setNombreClub(String nombreClub) {
		this.nombreClub = nombreClub;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public Long getCantidadIntegrantes() {
		return cantidadIntegrantes;
	}
	
	public void setCantidadIntegrantes(Long cantidadIntegrantes) {
		this.cantidadIntegrantes = cantidadIntegrantes;
	}
	
	public Boolean getEsPrivado() {
		return esPrivado;
	}
	
	public void setEsPrivado(Boolean esPrivado) {
		this.esPrivado = esPrivado;
	}
	
}
