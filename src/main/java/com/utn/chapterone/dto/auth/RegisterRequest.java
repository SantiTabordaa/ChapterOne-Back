package com.utn.chapterone.dto.auth;

public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String password;
    private String urlFotoPerfil;

    public RegisterRequest() {
    }

    public RegisterRequest(String nombre, String apellido, String email, String username, String password, String urlFotoPerfil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.username = username;
        this.password = password;
        this.urlFotoPerfil = urlFotoPerfil;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }
    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    
}