package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public List<Usuario> getAll() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    // @PostMapping("")
    // public Usuario save(@RequestBody Usuario usuario) {
    //     return usuarioService.crear(usuario);
    // } Comentado ya que lo va a manejar AuthController

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }
}
