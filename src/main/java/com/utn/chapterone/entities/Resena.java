package com.utn.chapterone.entities;

import java.time.LocalDate;

@Entity 
public class Resena {

	@id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idResena;
	private String contenido;
	private Double puntaje;
	private LocalDate fechaResena;
	@OneToOne
	@JoinColumn(name = "idLectura",  unique = true)
	@jsonBackReference //evita el error de referencia circular al serializar la lectura y la resena
	private Lectura lectura; 

	public Integer getIdResena() {
		return idResena;
	}
	public void setIdResena(Integer idResena) {
		this.idResena = idResena;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public Double getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(Double puntaje) {
		this.puntaje = puntaje;
	}
	public LocalDate getFechaResena() {
		return fechaResena;
	}
	public void setFechaResena(LocalDate fechaResena) {
		this.fechaResena = fechaResena;
	}
	
	public Lectura getLectura() {
		return lectura;
	}
	public void setLectura(Lectura lectura) {
		this.lectura = lectura;
	}
	
	 
	public Resena(Integer idResena, String contenido, Double puntaje, LocalDate fechaResena, Lectura lectura) {
		super();
		this.idResena = idResena;
		this.contenido = contenido;
		this.puntaje = puntaje;
		this.fechaResena = fechaResena;
		this.lectura = lectura;
	}
	
	public Resena() {
		super();
	}
}
