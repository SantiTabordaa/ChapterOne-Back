package com.utn.chapterone.controllers;

import com.utn.chapterone.dto.resena.ResenaInputDTO;
import com.utn.chapterone.dto.resena.ResenaOutputDTO;
import com.utn.chapterone.services.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    public ResponseEntity<List<ResenaOutputDTO>> obtenerTodos() {
        return ResponseEntity.ok(resenaService.obtenerTodos());
    }

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<ResenaOutputDTO>> obtenerPorLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(resenaService.obtenerPorLibro(idLibro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaOutputDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResenaOutputDTO> crear(@RequestBody ResenaInputDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.ownsResena(#id)")
    public ResponseEntity<ResenaOutputDTO> actualizar(@PathVariable Integer id, @RequestBody ResenaInputDTO dto) {
        return ResponseEntity.ok(resenaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.ownsResena(#id)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/denunciar")
    public ResponseEntity<ResenaOutputDTO> denunciar(
            @PathVariable Integer id,
            @RequestBody(required = false) Map<String, String> body
    ) {
        String motivo = body != null ? body.get("motivo") : null;
        return ResponseEntity.ok(resenaService.denunciar(id, motivo));
    }

    @PutMapping("/{id}/desestimar-denuncia")
    @PreAuthorize("@authz.isAdmin()")
    public ResponseEntity<ResenaOutputDTO> desestimarDenuncia(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.desestimarDenuncia(id));
    }
}
