package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Autor;
import com.utn.chapterone.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "http://localhost:5173")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> obtenerTodos() {
        return autorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Autor obtenerPorId(@PathVariable Integer id) {
        return autorService.obtenerPorId(id);
    }

    @PostMapping
    public Autor crear(@RequestBody Autor autor) {
        return autorService.crear(autor);
    }

    @PutMapping("/{id}")
    public Autor actualizar(@PathVariable Integer id, @RequestBody Autor autor) {
        return autorService.actualizar(id, autor);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        autorService.eliminar(id);
        return "Autor eliminado correctamente";
    }
}
