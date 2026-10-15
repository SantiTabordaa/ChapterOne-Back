package com.utn.chapterone.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utn.chapterone.security.Roles;

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

	@Column(nullable=false, length = 20)
	private String rol = Roles.USER; // "user", "admin", "moderator"

	@Column(nullable=false , unique = true)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable=false)
	private String password;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Solicitud> solicitudes; // Lista de solicitudes de ingreso al club con "aceptado" Muestra los clubes a los que pertenece. NO CREA COLUMNA

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
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		if (rol != null && !rol.isBlank()) {
			this.rol = rol.trim().toLowerCase();
		} else {
			this.rol = Roles.USER;
		}
	}
	public Boolean isAdmin() {
		return Roles.ADMIN.equalsIgnoreCase(this.rol);
	}
	public void setAdmin(Boolean admin) {
		this.rol = (Boolean.TRUE.equals(admin)) ? Roles.ADMIN : Roles.USER;
	}
	public boolean isModerator() {
		return Roles.MODERATOR.equalsIgnoreCase(this.rol);
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
			String rol, String username, String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.urlFotoPerfil = urlFotoPerfil;
		setRol(rol);
		this.username = username;
		this.password = password;
	}

	public Usuario(String nombre, String apellido, String email, String urlFotoPerfil,
			Boolean admin, String username, String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.urlFotoPerfil = urlFotoPerfil;
		setAdmin(admin);
		this.username = username;
		this.password = password;
	}
}
