package com.utn.chapterone.dto.usuario;

import com.utn.chapterone.entities.Usuario;

public class UsuarioRegistroDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String username;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsuarioRegistroDTO(Usuario usuario) {
        if (usuario != null) {
            this.nombre = usuario.getNombre();
            this.apellido = usuario.getApellido();
            this.email = usuario.getEmail();
            this.username = usuario.getUsername();
        }
        
    }
}
