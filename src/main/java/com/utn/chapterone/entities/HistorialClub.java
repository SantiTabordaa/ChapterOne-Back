package com.utn.chapterone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_club")
public class HistorialClub {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorial;			///Preguntar si es necesario un id para esta tabla o si se puede usar la combinacion de club-libro como clave primaria compuesta
    
    @ManyToOne
    @JoinColumn(name = "id_club")
    private Club club;
    
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
    
    @Column(nullable = false)
    private String mesAnio;
    
    public Integer getIdHistorial() {
        return idHistorial;
    }
    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }
    public Club getClub() {
        return club;
    }
    public void setClub(Club club) {
        this.club = club;
    }
    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    public String getMesAnio() {
        return mesAnio;
    }
    public void setMesAnio(String mesAnio) {
        this.mesAnio = mesAnio;
    }
    
    public HistorialClub() {
    }
    
    public HistorialClub(Club club, Libro libro, String mesAnio) {
        this.club = club;
        this.libro = libro;
        this.mesAnio = mesAnio;
    }
}
