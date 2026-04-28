package entities;

import java.time.LocalDate;

public class Reseña {

	private int idReseña;
	private String contenido;
	private double puntaje;
	private LocalDate fechaReseña;
	private Usuario user;
	private Libro libro;
	public int getIdReseña() {
		return idReseña;
	}
	public void setIdReseña(int idReseña) {
		this.idReseña = idReseña;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public double getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}
	public LocalDate getFechaReseña() {
		return fechaReseña;
	}
	public void setFechaReseña(LocalDate fechaReseña) {
		this.fechaReseña = fechaReseña;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public Reseña() {
	
	}
	
	
}
