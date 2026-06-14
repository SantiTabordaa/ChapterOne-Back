package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.genero.GeneroDTO;
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

    @GetMapping
    public List<GeneroDTO> obtenerTodos() {
    return generoService.obtenerTodosDTO();
}

    @GetMapping("/{id}")
    public Genero obtenerPorId(@PathVariable Integer id) {
        return generoService.obtenerPorId(id);
    }

    @PostMapping
    public Genero crear(@RequestBody Genero genero) {
        return generoService.crear(genero);
    }

    @PutMapping("/{id}")
    public Genero actualizar(@PathVariable Integer id, @RequestBody Genero genero) {
        return generoService.actualizar(id, genero);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        generoService.eliminarGenero(id);
        return "Género eliminado correctamente";
    }
}
   
