package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.services.LecturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturas")
public class LecturaController {

    @Autowired
    private LecturaService lecturaService;

    @GetMapping("")
    public List<Lectura> getAll() {
        return lecturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Lectura getById(@PathVariable Integer id) {
        return lecturaService.obtenerPorId(id);
    }

    @PostMapping("")
    public Lectura save(@RequestBody Lectura lectura) {
        return lecturaService.crear(lectura);
    }

    @PutMapping("/{id}")
    public Lectura update(@PathVariable Integer id, @RequestBody Lectura lectura) {
        return lecturaService.actualizar(id, lectura);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        lecturaService.eliminar(id);
    }
}
