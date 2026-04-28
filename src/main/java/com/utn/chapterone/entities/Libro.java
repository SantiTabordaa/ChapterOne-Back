package com.utn.chapterone.entities;

import jakarta.persistence.*; 

public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLibro;
	@Column(nullable = false)
	private String titulo;
	private String sinopsis;
	@Column(nullable = true)
	private Saga saga;
	private Integer cantPag;
	private Double valoracion;
	
	
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public Saga getSaga() {
		return saga;
	}
	public void setSaga(Saga saga) {
		this.saga = saga;
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
	public Integer getCantPag() {
		return cantPag;
	}
	public void setCantPag(Integer cantPag) {
		this.cantPag = cantPag;
	}
	public Double getValoracion() {
		//Se hace un promedio con las reseñas de el libro
		return valoracion;
	}
	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}
	
	public Libro(String titulo, String sinopsis, Saga saga, Integer cantPag, Double valoracion) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.saga = saga;
		this.cantPag = cantPag;
		this.valoracion = valoracion;
		
	}
	public Libro() {
		super();
	}
	
}
