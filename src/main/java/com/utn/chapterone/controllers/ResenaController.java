package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Resena;
import com.utn.chapterone.services.ResenaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    public List<Resena> obtenerTodos() {
        return resenaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Resena obtenerPorId(@PathVariable Integer id) {
        return resenaService.obtenerPorId(id);
    }

    @PostMapping
    public Resena crear(@RequestBody Resena resena) {
        return resenaService.crear(resena);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.ownsResena(#id)")
    public Resena actualizar(@PathVariable Integer id, @RequestBody Resena resena) {
        return resenaService.actualizar(id, resena);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.ownsResena(#id)")
    public String eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return "Reseña eliminada correctamente";
    }
}
