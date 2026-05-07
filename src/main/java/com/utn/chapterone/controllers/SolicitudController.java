package com.utn.chapterone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.utn.chapterone.services.SolicitudService;
import com.utn.chapterone.entities.Solicitud;


@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "http://localhost:5173")
public class SolicitudController {
    @Autowired
    private SolicitudService solicitudService;

    @GetMapping //  findAll
    public List<Solicitud> obtenerTodos() {
        return solicitudService.obtenerTodos();
    }

    @GetMapping("/{id}") //   getOne
    public Solicitud obtenerPorId(@PathVariable Integer id) {
        return solicitudService.obtenerPorId(id);
    }
    
    @PostMapping // Create
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return solicitudService.crear(solicitud);
    }

    @PutMapping("/{id}") // Update
    public Solicitud actualizar(@PathVariable Integer id, @RequestBody Solicitud solicitud){
        return solicitudService.actualizar(id, solicitud);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id){
        solicitudService.eliminar(id);
        return "Solicitud eliminada correctamente :)";
    }

}
