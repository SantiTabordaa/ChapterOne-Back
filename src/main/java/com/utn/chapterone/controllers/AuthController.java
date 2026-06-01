package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.auth.AuthResponse;
import com.utn.chapterone.dto.auth.LoginRequest;
import com.utn.chapterone.dto.usuario.UsuarioRegistroDTO;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.security.JwtService;
import com.utn.chapterone.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UsuarioService usuarioService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegistroDTO> register(@RequestBody Usuario usuario) {
        Usuario created = usuarioService.register(usuario);
        UsuarioRegistroDTO createdFiltrado = new UsuarioRegistroDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFiltrado);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.obtenerPorUsername(request.getUsername())
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales inválidas."
                ));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas."
            );
        }

        String token = jwtService.generateToken(
                usuario.getUsername(),
                Map.of("admin", usuario.isAdmin())
        );

        AuthResponse response = new AuthResponse(token, jwtService.getExpirationMs());
        return ResponseEntity.ok(response);
    }
}
