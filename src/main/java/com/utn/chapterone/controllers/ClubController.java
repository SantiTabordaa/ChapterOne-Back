package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Club;
import com.utn.chapterone.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClubController {

    @Autowired
    private ClubService clubService;

    // Traigo todos los clubes
    @GetMapping
    public List<Club> obtenerTodos() {
        return clubService.obtenerTodos();
    }

    // Traigo un club por ID
    @GetMapping("/{id}")
    public Club obtenerPorId(@PathVariable Integer id) {
        return clubService.obtenerPorId(id);
    }

    // Creo un nuevo club
    @PostMapping
    public Club crear(@RequestBody Club club) {
        return clubService.crear(club);
    }

    // Actualizo un club
    @PutMapping("/{id}")
    public Club actualizar(@PathVariable Integer id, @RequestBody Club club) {
        return clubService.actualizar(id, club);
    }

    // Elimino un club
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        clubService.eliminar(id);
    }
}
