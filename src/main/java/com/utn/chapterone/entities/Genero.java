package com.utn.chapterone.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "generos")
public class Genero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idGenero;

	@Column(nullable= false, unique = true)
	private String nombreGen;

	@OneToMany(mappedBy = "genero")
	private List<Club> clubes;
	
	@ManyToMany(mappedBy = "generos", fetch = FetchType.LAZY)
	private List<Libro> libros;

	public Integer getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}
	public String getNombreGen() {
		return nombreGen;
	}
	public void setNombreGen(String nombreGen) {
		this.nombreGen = nombreGen;
	}

	public void setClubes(List<Club> clubes) {
		this.clubes = clubes;
	}
	public List<Club> getClubes() {
		return clubes;
	}
	public List<Libro> getLibros() {
		return libros;
	}
	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
	
	public Genero(int idGenero, String nombreGen) {
		this.nombreGen = nombreGen;
	}
	
	public Genero() {
		
	}

}
