package entities;

import java.util.Date;

public class Membresia {

	private Usuario user;
	private Club club;
	private String estado; //Puede estar Pendiente-Aceptado-Rechazado 
	private Date fechaUnion;
	
	
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
	
	
	public Membresia() {
		
	}
	
}
