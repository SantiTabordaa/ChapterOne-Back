package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.lectura.LecturaInputDTO;
import com.utn.chapterone.dto.lectura.LecturaOutputDTO;
import com.utn.chapterone.services.LecturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturas")
public class LecturaController {

    private final LecturaService lecturaService;

    public LecturaController(LecturaService lecturaService) {
        this.lecturaService = lecturaService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LecturaOutputDTO>> getAll() {
        return ResponseEntity.ok(lecturaService.obtenerTodos());
    }

    @GetMapping("/mis-lecturas")
    public ResponseEntity<List<LecturaOutputDTO>> getMisLecturas() {
        return ResponseEntity.ok(lecturaService.obtenerMisLecturas());
    }

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<LecturaOutputDTO> getMiLecturaPorLibro(@PathVariable Integer idLibro) {
        return lecturaService.obtenerMiLecturaPorLibro(idLibro)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public ResponseEntity<LecturaOutputDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(lecturaService.obtenerPorId(id));
    }

    @PostMapping("")
    public ResponseEntity<LecturaOutputDTO> save(@RequestBody LecturaInputDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lecturaService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public ResponseEntity<LecturaOutputDTO> update(@PathVariable Integer id, @RequestBody LecturaInputDTO dto) {
        return ResponseEntity.ok(lecturaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.ownsLectura(#id)")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        lecturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
