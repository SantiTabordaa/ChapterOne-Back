package com.utn.chapterone.entities;
import jakarta.persistence.*;



@Entity
@Table(name = "club")
public class Club {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idClub;
	@Column(nullable=false , unique = true)
	private String nombreClub;
	@Column(length = 200)
	private String descrip;
	@ManyToOne
	@JoinColumn(name = "id_genero") // Clave foránea para Género
	private Genero genero; 
	@ManyToOne
	@JoinColumn(name = "id_admin") // Clave foránea para Usuario (admin del club)
	private Usuario admin; 
	private Boolean privado = false; // Por defecto, un club no es privado
	@oneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PosteoClub> posteos; // Lista de posteos asociados al club. NO CREA COLUMNA 
	@oneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MiembroClub> miembros; // Lista de miembros asociados al club. NO CREA COLUMNA

	public Integer getIdClub() {
		return idClub;
	}
	public void setIdClub(Integer idClub) {
		this.idClub = idClub;
	}
	public String getNombreClub() {
		return nombreClub;
	}
	public void setNombreClub(String nombreClub) {
		this.nombreClub = nombreClub;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public Usuario getAdmin() {
		return admin;
	}
	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}
	public Boolean isPrivado() {
		return privado;
	}
	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	


	public Club() {
		super();
	}
	public Club(String nombreClub, String descrip, Genero genero, Usuario admin, Boolean privado) {
		super();
		this.nombreClub = nombreClub;
		this.descrip = descrip;
		this.genero = genero;
		this.admin = admin;
		this.privado = privado;
	}
	
}
