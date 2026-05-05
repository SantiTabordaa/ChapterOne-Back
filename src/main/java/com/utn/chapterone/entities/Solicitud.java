package com.utn.chapterone.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSolicitud;
	private String estado; //Puede estar Pendiente-Aceptado-Rechazado 
	private Date fechaUnion;
	private String rol; //opcional, se pone cuando es aceptado, ya que puede ser un miembro/moderador/administrador
	@ManyToOne
	@JoinColumn(name = "usuario_solicitante", nullable = false)
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "club_solicitado", nullable = false)
	private Club club;
	
	public Integer getIdSolicitud() {
		return idSolicitud;
	}	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
	//Constructor sin parámetros
	public Solicitud() {
		
	}

	//Constructor completo
	public Solicitud(String estado, Date fechaUnion, String rol, Usuario usuario, Club club) {
        this.estado = estado;
        this.fechaUnion = fechaUnion;
        this.rol = rol;
        this.usuario = usuario;
        this.club = club;
    }
	
}
