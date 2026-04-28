package com.utn.chapterone.entities;
import java.time.LocalDateTime;

public class PosteoClub {

	private int idPosteo;
	private String contenido;
	private LocalDateTime fechaHoraCreacion;
	private Usuario user;
	private Club club;
	public int getIdPosteo() {
		return idPosteo;
	}
	public void setIdPosteo(int idPosteo) {
		this.idPosteo = idPosteo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public LocalDateTime getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}
	public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	
	public PosteoClub() {
		
	}
}
