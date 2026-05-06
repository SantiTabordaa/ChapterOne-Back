package com.utn.chapterone.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posteos_club")
public class PosteoClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPosteo;

    @Column(nullable = false, length = 500)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaHoraCreacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_club")
    private Club club;

    public PosteoClub() {
    }

    public PosteoClub(String contenido, LocalDateTime fechaHoraCreacion, Usuario usuario, Club club) {
        this.contenido = contenido;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.usuario = usuario;
        this.club = club;
    }

    public Integer getIdPosteo() {
        return idPosteo;
    }

    public void setIdPosteo(Integer idPosteo) {
        this.idPosteo = idPosteo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
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
}
