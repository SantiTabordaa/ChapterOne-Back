package com.utn.chapterone.entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity 
public class Resena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idResena;
	private String contenido;
	private Double puntaje;
	private LocalDate fechaResena;
	@Column(columnDefinition = "boolean default false")
	private Boolean denunciada = false;
	private String motivoDenuncia;
	@OneToOne
	@JoinColumn(name = "id_lectura",  unique = true, nullable = false) //le decimos a jpa que la columna se va a llamar idLectura, que es unica y no puede ser nula
	@JsonBackReference //evita el error de referencia circular al serializar la lectura y la resena
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
	public Boolean getDenunciada() {
		return denunciada;
	}
	public void setDenunciada(Boolean denunciada) {
		this.denunciada = denunciada;
	}
	public String getMotivoDenuncia() {
		return motivoDenuncia;
	}
	public void setMotivoDenuncia(String motivoDenuncia) {
		this.motivoDenuncia = motivoDenuncia;
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
