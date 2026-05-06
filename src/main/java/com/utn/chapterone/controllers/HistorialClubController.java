package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.HistorialClub;
import com.utn.chapterone.services.HistorialClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@CrossOrigin(origins = "http://localhost:5173")
public class HistorialClubController {

    @Autowired
    private HistorialClubService service;

    @GetMapping
    public List<HistorialClub> all() { return service.obtenerTodos(); }

    @GetMapping("/{id}")
    public HistorialClub get(@PathVariable Integer id) { return service.obtenerPorId(id); }

    @GetMapping("/club/{clubId}")
    public List<HistorialClub> byClub(@PathVariable Integer clubId) { return service.obtenerPorClub(clubId); }

    @GetMapping("/libro/{libroId}")
    public List<HistorialClub> byLibro(@PathVariable Integer libroId) { return service.obtenerPorLibro(libroId); }

    @PostMapping
    public HistorialClub crear(@RequestBody HistorialClub h) { return service.crear(h); }

    @PutMapping("/{id}")
    public HistorialClub actualizar(@PathVariable Integer id, @RequestBody HistorialClub h) { return service.actualizar(id, h); }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "Historial eliminado";
    }
}
