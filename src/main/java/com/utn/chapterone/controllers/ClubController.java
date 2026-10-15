package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.club.ClubListadoDTO;
import com.utn.chapterone.entities.Club;
import com.utn.chapterone.services.ClubService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public List<Club> obtenerTodos() {
        return clubService.obtenerTodos();
    }

    @GetMapping("/listado")
    public List<ClubListadoDTO> obtenerTodosListado() {
        return clubService.obtenerTodosListado();
    }

    @GetMapping("/{id}")
    public Club obtenerPorId(@PathVariable Integer id) {
        return clubService.obtenerPorId(id);
    }

    @PostMapping
    public Club crear(@RequestBody Club club) {
        return clubService.crear(club);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.isClubAdmin(#id)")
    public Club actualizar(@PathVariable Integer id, @RequestBody Club club) {
        return clubService.actualizar(id, club);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.isClubAdmin(#id)")
    public void eliminar(@PathVariable Integer id) {
        clubService.eliminar(id);
    }
}
