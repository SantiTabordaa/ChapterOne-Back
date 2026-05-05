package com.utn.chapterone.entities;

import java.util.Date;

public class Solicitud {

	private Usuario user;
	private Club club;
	private String estado; //Puede estar Pendiente-Aceptado-Rechazado 
	private Date fechaUnion;
	private String rol; //opcional, se pone cuando 
	
	
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaUnion() {
		return fechaUnion;
	}
	public void setFechaUnion(Date fechaUnion) {
		this.fechaUnion = fechaUnion;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public Solicitud() {
		
	}
	
}
