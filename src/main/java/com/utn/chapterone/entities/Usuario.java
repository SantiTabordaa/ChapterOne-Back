package com.utn.chapterone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	private String nombre;
	private String apellido;
	@Column(nullable=false , unique = true)
	private String email;
	private String urlFotoPerfil;
	@Column(nullable=false)
	private Boolean admin = false; // Por defecto, un usuario no es admin
	@Column(nullable=false , unique = true)
	private String username;
	@Column(nullable=false)
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}
	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Usuario() {
		super();
	}

	public Usuario(String nombre, String apellido, String email, String urlFotoPerfil,
			Boolean admin, String username, String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.urlFotoPerfil = urlFotoPerfil;
		this.admin = admin;
		this.username = username;
		this.password = password;
	}
	
	
}
