package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.autor.AutorDetalleDto;
import com.utn.chapterone.dto.autor.AutorListadoDto;
import com.utn.chapterone.entities.Autor;
import com.utn.chapterone.services.AutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<Autor> obtenerTodos() {
        return autorService.obtenerTodos();
    }

    @GetMapping("/listado")
    public List<AutorListadoDto> obtenerListado() {
        return autorService.obtenerListado();
    }

    @GetMapping("/{id}")
    public AutorDetalleDto obtenerPorId(@PathVariable Integer id) {
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
