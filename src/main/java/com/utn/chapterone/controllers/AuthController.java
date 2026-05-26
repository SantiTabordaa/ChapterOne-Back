package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.usuario.UsuarioRegistroDTO;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegistroDTO> register(@RequestBody Usuario usuario) {
        Usuario created = usuarioService.register(usuario);
        UsuarioRegistroDTO createdFiltrado = new UsuarioRegistroDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFiltrado);
    }
    


}
