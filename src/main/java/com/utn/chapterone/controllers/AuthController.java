package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.services.UsuarioService;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {
    
    UsuarioService usuarioService;

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.register(usuario);
    }
    


}
