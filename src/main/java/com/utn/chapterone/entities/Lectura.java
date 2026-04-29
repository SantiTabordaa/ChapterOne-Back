package com.utn.chapterone.entities;
import java.util.Date;

@Entity
@Table(name="lecturas")
public class Lectura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLectura;
	@ManyToOne
	@JoinColumn(name="idUsuario") @nullable(false)
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name="idLibro") @nullable(false)  //le decimos a jpa como se va a llamar la columna 
	private Libro libro;
	private String estado; //leido,leyendo,quiero leer
	private Date fechaIni;
	private Date fechaFin;
	@OneToOne(mappedBy = "lectura", cascade = CascadeType.ALL) //le decimos a jpa que la relacion ya esta mapeada en la clase resena, y que se mapea a traves del atributo lectura de la clase resena
	private Resena resena; //relacion uno a uno con resena, una lectura tiene una resena y una resena tiene una lectura
	
	public Integer getIdLectura() {
		return idLectura;
	}
	public void setIdLectura(Integer idLectura) {
		this.idLectura = idLectura;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Resena getResena() {
		return resena;
	}
	public void agregarResena(Resena resena) {
		this.resena = resena;
		resena.setLectura(this); //le decimos a la resena que su lectura es esta lectura, para mantener la relacion bidireccional entre lectura y resena
	}

	public Lectura() {
		super();
	}

	public Lectura(Integer idLectura, Usuario usuario, Libro libro, String estado, Date fechaIni, Date fechaFin) {
		super();
		this.idLectura = idLectura;
		this.usuario = usuario;
		this.libro = libro;
		this.estado = estado;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
	}
}
