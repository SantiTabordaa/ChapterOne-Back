package com.utn.chapterone.entities;

public class Genero {
	
	private int idGenero;
	private String nombreGen;
	
	public int getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}
	public String getNombreGen() {
		return nombreGen;
	}
	public void setNombreGen(String nombreGen) {
		this.nombreGen = nombreGen;
	}
	
	public Genero(int idGenero, String nombreGen) {
		this.idGenero = idGenero;
		this.nombreGen = nombreGen;
	}
	
	public Genero() {
		
	}

}
