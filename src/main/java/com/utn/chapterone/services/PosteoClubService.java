package com.utn.chapterone.services;

import com.utn.chapterone.entities.PosteoClub;
import com.utn.chapterone.repositories.PosteoClubRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PosteoClubService {

    private final PosteoClubRepository posteoClubRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authz;

    public PosteoClubService(
            PosteoClubRepository posteoClubRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authz
    ) {
        this.posteoClubRepository = posteoClubRepository;
        this.usuarioRepository = usuarioRepository;
        this.authz = authz;
    }

    public List<PosteoClub> obtenerTodos() {
        return posteoClubRepository.findAll();
    }

    public PosteoClub obtenerPorId(Integer id) {
        return posteoClubRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posteo no encontrado"));
    }

    public PosteoClub crear(PosteoClub posteoClub) {
        Integer uid = authz.currentUserId();
        if (uid != null) {
            usuarioRepository.findById(uid).ifPresent(posteoClub::setUsuario);
        }
        if (posteoClub.getFechaHoraCreacion() == null) {
            posteoClub.setFechaHoraCreacion(LocalDateTime.now());
        }
        return posteoClubRepository.save(posteoClub);
    }

    public PosteoClub actualizar(Integer id, PosteoClub posteoActualizado) {
        PosteoClub posteo = obtenerPorId(id);

        posteo.setContenido(posteoActualizado.getContenido());
        if (authz.isAdmin()) {
            if (posteoActualizado.getUsuario() != null) {
                posteo.setUsuario(posteoActualizado.getUsuario());
            }
            if (posteoActualizado.getClub() != null) {
                posteo.setClub(posteoActualizado.getClub());
            }
        }

        return posteoClubRepository.save(posteo);
    }

    public void eliminar(Integer id) {
        PosteoClub posteo = obtenerPorId(id);
        posteoClubRepository.delete(posteo);
    }
}
