package com.utn.chapterone.services;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Boolean existeUsuario(String username){
        if(usuarioRepository.existsByUsername(username)){
            return true;
        }
        return false;
    }

    public Boolean existeEmail(String email){
        if(usuarioRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }

    public Usuario register(Usuario usuario) {
        if (existeUsuario(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe.");
        }
        if (existeEmail(usuario.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado.");
        }


        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // usuario.setPassword(encoder.encode(usuario.getPassword()));

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Integer id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setUrlFotoPerfil(usuarioActualizado.getUrlFotoPerfil());
        usuario.setAdmin(usuarioActualizado.isAdmin());
        usuario.setUsername(usuarioActualizado.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuarioRepository.deleteById(id);
    }
    
    //Inyeccion de Bean 'PasswordEncoder'
    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public String hashPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }
}