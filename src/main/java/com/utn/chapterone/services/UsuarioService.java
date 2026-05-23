package com.utn.chapterone.services;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public Usuario register(Usuario usuario) {
        boolean exists = existeUsuario(usuario.getUsername());

        if (exists){
            return null;
        }
        else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }
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
        usuario.setPassword(usuarioActualizado.getPassword());
        
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuarioRepository.deleteById(id);
    }
}