package com.utn.chapterone.services;

import com.utn.chapterone.dto.auth.RegisterRequest;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

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

    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario register(RegisterRequest registerRequest) {
        if(validar_campos(registerRequest)){
            if (existeUsuario(registerRequest.getUsername())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe.");
            }
            if (existeEmail(registerRequest.getEmail())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado.");
            }

            Usuario usuario = new Usuario();

            usuario.setNombre(registerRequest.getNombre());
            usuario.setApellido(registerRequest.getApellido());
            usuario.setEmail(registerRequest.getEmail());
            usuario.setUsername(registerRequest.getUsername());
            usuario.setAdmin(false);
            //TODO: hacer funcion que guarde la foto y setee este atributo con la ruta.
            usuario.setUrlFotoPerfil("not implemented"); 
            // encoder de password
            usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    private Boolean validar_campos(RegisterRequest peticion){
        if(peticion.getApellido() == null){
            return false;
        }
        if(peticion.getNombre() == null){
            return false;
        }
        if(peticion.getEmail() == null){
            return false;
        }
        if(peticion.getUsername() == null){
            return false;
        }
        if(peticion.getPassword() == null){
            return false;
        }
        return true;
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
