package com.utn.chapterone.services;

import com.utn.chapterone.entities.PosteoClub;
import com.utn.chapterone.repositories.PosteoClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosteoClubService {

    @Autowired
    private PosteoClubRepository posteoClubRepository;

    public List<PosteoClub> obtenerTodos() {
        return posteoClubRepository.findAll();
    }

    public PosteoClub obtenerPorId(Integer id) {
        return posteoClubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Posteo no encontrado"));
    }

    public PosteoClub crear(PosteoClub posteoClub) {
        return posteoClubRepository.save(posteoClub);
    }

    public PosteoClub actualizar(Integer id, PosteoClub posteoActualizado) {
        PosteoClub posteo = posteoClubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Posteo no encontrado"));
        
        posteo.setContenido(posteoActualizado.getContenido());
        posteo.setFechaHoraCreacion(posteoActualizado.getFechaHoraCreacion());
        posteo.setUsuario(posteoActualizado.getUsuario());
        posteo.setClub(posteoActualizado.getClub());
        
        return posteoClubRepository.save(posteo);
    }

    public void eliminar(Integer id) {
        posteoClubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Posteo no encontrado"));
        
        posteoClubRepository.deleteById(id);
    }
}
