package com.utn.chapterone.controllers;

import com.utn.chapterone.dtos.libro.LibroInputDTO;
import com.utn.chapterone.dtos.libro.LibroOutputDTO;
import com.utn.chapterone.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "http://localhost:5173")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("")
    public ResponseEntity<List<LibroOutputDTO>> getAll() {
        return ResponseEntity.ok(libroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroOutputDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(libroService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<LibroOutputDTO> save(@RequestBody LibroInputDTO libroInputDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.save(libroInputDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroOutputDTO> update(@PathVariable Integer id, @RequestBody LibroInputDTO libroInputDTO) {
        return ResponseEntity.ok(libroService.update(id, libroInputDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

