package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.HistorialClub;
import com.utn.chapterone.services.HistorialClubService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
public class HistorialClubController {

    private final HistorialClubService service;

    public HistorialClubController(HistorialClubService service) {
        this.service = service;
    }

    @GetMapping
    public List<HistorialClub> all() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public HistorialClub get(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/club/{clubId}")
    public List<HistorialClub> byClub(@PathVariable Integer clubId) {
        return service.obtenerPorClub(clubId);
    }

    @GetMapping("/libro/{libroId}")
    public List<HistorialClub> byLibro(@PathVariable Integer libroId) {
        return service.obtenerPorLibro(libroId);
    }

    @PostMapping
    @PreAuthorize("@authz.isClubAdmin(#h.club.idClub)")
    public HistorialClub crear(@RequestBody HistorialClub h) {
        return service.crear(h);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.canManageHistorial(#id)")
    public HistorialClub actualizar(@PathVariable Integer id, @RequestBody HistorialClub h) {
        return service.actualizar(id, h);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.canManageHistorial(#id)")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "Historial eliminado";
    }
}
