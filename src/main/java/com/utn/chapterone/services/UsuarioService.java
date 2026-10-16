package com.utn.chapterone.services;

import com.utn.chapterone.dto.auth.RegisterRequest;
import com.utn.chapterone.dto.usuario.UsuarioUpdateDTO;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.Roles;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Boolean existeUsuario(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public Boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario register(RegisterRequest registerRequest) {
        if (!validarCampos(registerRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltan campos requeridos.");
        }

        if (existeUsuario(registerRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe.");
        }
        if (existeEmail(registerRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registerRequest.getNombre());
        usuario.setApellido(registerRequest.getApellido());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setUsername(registerRequest.getUsername());
        usuario.setRol(Roles.USER); // Por defecto rol de usuario estándar
        usuario.setUrlFotoPerfil(registerRequest.getUrlFotoPerfil());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return usuarioRepository.save(usuario);
    }

    private Boolean validarCampos(RegisterRequest peticion) {
        return peticion.getApellido() != null &&
                peticion.getNombre() != null &&
                peticion.getEmail() != null &&
                peticion.getUsername() != null &&
                peticion.getPassword() != null;
    }

    public Usuario actualizar(Integer id, UsuarioUpdateDTO dto, boolean callerIsAdmin) {
        Usuario usuario = obtenerPorId(id);

        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            usuario.setApellido(dto.getApellido());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getUrlFotoPerfil() != null) {
            usuario.setUrlFotoPerfil(dto.getUrlFotoPerfil());
        }
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            usuario.setUsername(dto.getUsername());
        }

        // Solo un administrador puede modificar el rol de un usuario
        if (dto.getRol() != null && !dto.getRol().isBlank()) {
            if (callerIsAdmin) {
                if (Roles.esRolValido(dto.getRol())) {
                    usuario.setRol(dto.getRol());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido.");
                }
            }
        }

        // Validación y encriptado si se provee nueva contraseña
        String newPassword = dto.getPassword();
        if (newPassword != null && !newPassword.isBlank()) {
            if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La contraseña no cumple con los requisitos mínimos de seguridad.");
            }
            usuario.setPassword(passwordEncoder.encode(newPassword));
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }

    public String hashPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }
}
