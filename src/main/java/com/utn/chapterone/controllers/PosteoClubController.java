package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.PosteoClub;
import com.utn.chapterone.services.PosteoClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posteos")
@CrossOrigin(origins = "http://localhost:5173")
public class PosteoClubController {

    @Autowired
    private PosteoClubService posteoClubService;

    // GET: Traer todos los posteos
    @GetMapping
    public List<PosteoClub> obtenerTodos() {
        return posteoClubService.obtenerTodos();
    }

    // GET: Traer un posteo por ID
    @GetMapping("/{id}")
    public PosteoClub obtenerPorId(@PathVariable Integer id) {
        return posteoClubService.obtenerPorId(id);
    }

    // POST: Crear un nuevo posteo
    @PostMapping
    public PosteoClub crear(@RequestBody PosteoClub posteoClub) {
        return posteoClubService.crear(posteoClub);
    }

    // PUT: Actualizar un posteo
    @PutMapping("/{id}")
    public PosteoClub actualizar(@PathVariable Integer id, @RequestBody PosteoClub posteoClub) {
        return posteoClubService.actualizar(id, posteoClub);
    }

    // DELETE: Eliminar un posteo
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        posteoClubService.eliminar(id);
        return "Posteo eliminado correctamente";
    }
}
