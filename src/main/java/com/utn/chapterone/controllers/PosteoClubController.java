package com.utn.chapterone.controllers;

import com.utn.chapterone.entities.PosteoClub;
import com.utn.chapterone.services.PosteoClubService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posteos")
public class PosteoClubController {

    private final PosteoClubService posteoClubService;

    public PosteoClubController(PosteoClubService posteoClubService) {
        this.posteoClubService = posteoClubService;
    }

    @GetMapping
    public List<PosteoClub> obtenerTodos() {
        return posteoClubService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public PosteoClub obtenerPorId(@PathVariable Integer id) {
        return posteoClubService.obtenerPorId(id);
    }

    @PostMapping
    public PosteoClub crear(@RequestBody PosteoClub posteoClub) {
        return posteoClubService.crear(posteoClub);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.canEditPosteo(#id)")
    public PosteoClub actualizar(@PathVariable Integer id, @RequestBody PosteoClub posteoClub) {
        return posteoClubService.actualizar(id, posteoClub);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.canDeletePosteo(#id)")
    public String eliminar(@PathVariable Integer id) {
        posteoClubService.eliminar(id);
        return "Posteo eliminado correctamente";
    }
}
