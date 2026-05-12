package com.utn.chapterone.entities;
import java.util.List;

import jakarta.persistence.*; 

@Entity
@Table(name="libros")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLibro;
	@Column(nullable = false, unique = true)
	private String titulo;
	private String sinopsis;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_saga")
	private Saga saga;
	private Integer nroTomo;
	private Integer cantPag;
	@Transient //No lo calcula la bd, lo calculamos nosotros
	private Double valoracion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "libros_autores",
		joinColumns = @JoinColumn(name = "id_libro"),
		inverseJoinColumns = @JoinColumn(name = "id_autor")
	)
	private List<Autor> autores;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "libros_generos",
		joinColumns = @JoinColumn(name = "id_libro"),
		inverseJoinColumns = @JoinColumn(name = "id_genero")
	)
	private List<Genero> generos;
	
	@OneToMany(mappedBy = "libro")
	private List<HistorialClub> historialesLibro;

	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public Saga getSaga() {
		return saga;
	}
	public void setSaga(Saga saga) {
		this.saga = saga;
	}
	public Integer getNroTomo() {
		return nroTomo;
	}
	public void setNroTomo(Integer nroTomo) {
		this.nroTomo = nroTomo;
	}
	public Integer getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getCantPag() {
		return cantPag;
	}
	public void setCantPag(Integer cantPag) {
		this.cantPag = cantPag;
	}
	public Double getValoracion() {
		return valoracion;
	}
	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}

	public List<Autor> getAutores(){
		return autores;
	}
	public void setAutores(List<Autor> autores) {

		this.autores = autores;
	}
	public List<Genero> getGeneros() {
		return generos;
	}
	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}
	public List<HistorialClub> getHistorialesLibro() {
		return historialesLibro;
	}
	public void setHistorialesLibro(List<HistorialClub> historialesLibro) {
		this.historialesLibro = historialesLibro;
	}
	
  	public Libro(String titulo, String sinopsis, Saga saga, Integer nroTomo, Integer cantPag, Double valoracion) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.saga = saga;
		this.nroTomo = nroTomo;
		this.cantPag = cantPag;
		this.valoracion = valoracion;
	}

	public Libro() {
		super();

}
}
