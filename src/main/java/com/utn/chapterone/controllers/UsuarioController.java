package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.usuario.UsuarioPublicoDTO;
import com.utn.chapterone.dto.usuario.UsuarioUpdateDTO;
import com.utn.chapterone.security.AuthorizationService;
import com.utn.chapterone.services.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthorizationService authz;

    public UsuarioController(UsuarioService usuarioService, AuthorizationService authz) {
        this.usuarioService = usuarioService;
        this.authz = authz;
    }

    @GetMapping("")
    public List<UsuarioPublicoDTO> getAll() {
        return usuarioService.obtenerTodos().stream()
                .map(UsuarioPublicoDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioPublicoDTO getById(@PathVariable Integer id) {
        return new UsuarioPublicoDTO(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.isSelf(#id)")
    public UsuarioPublicoDTO update(@PathVariable Integer id, @RequestBody UsuarioUpdateDTO dto) {
        return new UsuarioPublicoDTO(usuarioService.actualizar(id, dto, authz.isAdmin()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.isSelf(#id)")
    public void delete(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }
}
