package com.utn.chapterone.services;

import com.utn.chapterone.entities.Resena;
import com.utn.chapterone.repositories.ResenaRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final AuthorizationService authz;

    public ResenaService(ResenaRepository resenaRepository, AuthorizationService authz) {
        this.resenaRepository = resenaRepository;
        this.authz = authz;
    }

    public List<Resena> obtenerTodos() {
        return resenaRepository.findAll();
    }

    public Resena obtenerPorId(Integer id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
    }

    public Resena crear(Resena resena) {
        if (resena.getLectura() != null && resena.getLectura().getIdLectura() != null) {
            if (!authz.ownsLectura(resena.getLectura().getIdLectura())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes crear una reseña para una lectura ajena.");
            }
        }
        if (resena.getFechaResena() == null) {
            resena.setFechaResena(LocalDate.now());
        }
        return resenaRepository.save(resena);
    }

    public Resena actualizar(Integer id, Resena resenaActualizada) {
        Resena resena = obtenerPorId(id);

        resena.setContenido(resenaActualizada.getContenido());
        resena.setPuntaje(resenaActualizada.getPuntaje());
        if (resenaActualizada.getFechaResena() != null) {
            resena.setFechaResena(resenaActualizada.getFechaResena());
        }
        if (authz.isAdmin() && resenaActualizada.getLectura() != null) {
            resena.setLectura(resenaActualizada.getLectura());
        }

        return resenaRepository.save(resena);
    }

    public void eliminar(Integer id) {
        Resena resena = obtenerPorId(id);
        resenaRepository.delete(resena);
    }
}
