package com.utn.chapterone.services;

import com.utn.chapterone.entities.HistorialClub;
import com.utn.chapterone.repositories.ClubRepository;
import com.utn.chapterone.repositories.HistorialClubRepository;
import com.utn.chapterone.repositories.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HistorialClubService {

    private final HistorialClubRepository historialRepo;
    private final ClubRepository clubRepo;
    private final LibroRepository libroRepo;

    public HistorialClubService(
            HistorialClubRepository historialRepo,
            ClubRepository clubRepo,
            LibroRepository libroRepo
    ) {
        this.historialRepo = historialRepo;
        this.clubRepo = clubRepo;
        this.libroRepo = libroRepo;
    }

    public List<HistorialClub> obtenerTodos() {
        return historialRepo.findAll();
    }

    public HistorialClub obtenerPorId(Integer id) {
        return historialRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historial no encontrado"));
    }

    public List<HistorialClub> obtenerPorClub(Integer clubId) {
        return historialRepo.findByClubIdClub(clubId);
    }

    public List<HistorialClub> obtenerPorLibro(Integer libroId) {
        return historialRepo.findByLibroIdLibro(libroId);
    }

    public HistorialClub crear(HistorialClub h) {
        if (h.getClub() == null || h.getClub().getIdClub() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar un club válido");
        }
        if (h.getLibro() == null || h.getLibro().getIdLibro() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar un libro válido");
        }
        clubRepo.findById(h.getClub().getIdClub())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club no encontrado"));
        libroRepo.findById(h.getLibro().getIdLibro())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
        return historialRepo.save(h);
    }

    public HistorialClub actualizar(Integer id, HistorialClub actualizado) {
        HistorialClub h = obtenerPorId(id);
        h.setMesAnio(actualizado.getMesAnio());
        if (actualizado.getLibro() != null) {
            h.setLibro(actualizado.getLibro());
        }
        if (actualizado.getClub() != null) {
            h.setClub(actualizado.getClub());
        }
        return historialRepo.save(h);
    }

    public void eliminar(Integer id) {
        HistorialClub h = obtenerPorId(id);
        historialRepo.delete(h);
    }
}
