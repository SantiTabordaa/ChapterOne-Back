package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.services.LecturaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturas")
public class LecturaController {

    private final LecturaService lecturaService;

    public LecturaController(LecturaService lecturaService) {
        this.lecturaService = lecturaService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Lectura> getAll() {
        return lecturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public Lectura getById(@PathVariable Integer id) {
        return lecturaService.obtenerPorId(id);
    }

    @PostMapping("")
    public Lectura save(@RequestBody Lectura lectura) {
        return lecturaService.crear(lectura);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public Lectura update(@PathVariable Integer id, @RequestBody Lectura lectura) {
        return lecturaService.actualizar(id, lectura);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public void delete(@PathVariable Integer id) {
        lecturaService.eliminar(id);
    }
}
