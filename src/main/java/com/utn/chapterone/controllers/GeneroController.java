package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Genero;
import com.utn.chapterone.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "http://localhost:5173")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    // GET: Traer todos los géneros
    @GetMapping
    public List<Genero> obtenerTodos() {
        return generoService.obtenerTodos();
    }

    // GET: Traer un género por ID
    @GetMapping("/{id}")
    public Genero obtenerPorId(@PathVariable Integer id) {
        return generoService.obtenerPorId(id);
    }

    // POST: Crear un nuevo género
    @PostMapping
    public Genero crear(@RequestBody Genero genero) {
        return generoService.crear(genero);
    }

    // PUT: Actualizar un género
    @PutMapping("/{id}")
    public Genero actualizar(@PathVariable Integer id, @RequestBody Genero genero) {
        return generoService.actualizar(id, genero);
    }

    // DELETE: Eliminar un género (con validación de clubes)
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        generoService.eliminarGenero(id);
        return "Género eliminado correctamente";
    }
}
