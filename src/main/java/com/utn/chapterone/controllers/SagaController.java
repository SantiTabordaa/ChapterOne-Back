package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.Saga;
import com.utn.chapterone.services.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sagas")
@CrossOrigin(origins = "http://localhost:5173")
public class SagaController {

    @Autowired
    private SagaService sagaService;

    @GetMapping
    public List<Saga> obtenerTodos() {
        return sagaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Saga obtenerPorId(@PathVariable Integer id) {
        return sagaService.obtenerPorId(id);
    }

    @PostMapping
    public Saga crear(@RequestBody Saga saga) {
        return sagaService.crear(saga);
    }

    @PutMapping("/{id}")
    public Saga actualizar(@PathVariable Integer id, @RequestBody Saga saga) {
        return sagaService.actualizar(id, saga);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        sagaService.eliminar(id);
        return "Saga eliminada correctamente";
    }
}
