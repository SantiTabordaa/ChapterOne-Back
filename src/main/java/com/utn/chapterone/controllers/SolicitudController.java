package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Solicitud;
import com.utn.chapterone.services.SolicitudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public List<Solicitud> obtenerTodos() {
        return solicitudService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Solicitud obtenerPorId(@PathVariable Integer id) {
        return solicitudService.obtenerPorId(id);
    }

    @PostMapping
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return solicitudService.crear(solicitud);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.canManageSolicitud(#id)")
    public Solicitud actualizar(@PathVariable Integer id, @RequestBody Solicitud solicitud) {
        return solicitudService.actualizar(id, solicitud);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.canDeleteSolicitud(#id)")
    public String eliminar(@PathVariable Integer id) {
        solicitudService.eliminar(id);
        return "Solicitud eliminada correctamente :)";
    }
}
