package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("")
    public List<Libro> getAll() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Libro getById(@PathVariable Integer id) {
        return libroService.obtenerPorId(id);
    }

    @PostMapping("")
    public Libro save(@RequestBody Libro libro) {
        return libroService.crear(libro);
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable Integer id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        libroService.eliminar(id);
    }
}

