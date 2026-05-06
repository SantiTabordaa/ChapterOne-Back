package com.utn.chapterone.services;

import com.utn.chapterone.entities.HistorialClub;
import com.utn.chapterone.repositories.HistorialClubRepository;
import com.utn.chapterone.repositories.ClubRepository;
import com.utn.chapterone.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialClubService {

    @Autowired
    private HistorialClubRepository historialRepo;
    @Autowired
    private ClubRepository clubRepo;
    @Autowired
    private LibroRepository libroRepo;

    public List<HistorialClub> obtenerTodos() {
        return historialRepo.findAll();
    }

    public HistorialClub obtenerPorId(Integer id) {
        return historialRepo.findById(id).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public List<HistorialClub> obtenerPorClub(Integer clubId) {
        return historialRepo.findByClubIdHistorial(clubId);
    }

    public List<HistorialClub> obtenerPorLibro(Integer libroId) {
        return historialRepo.findByLibroIdHistorial(libroId);
    }

    public HistorialClub crear(HistorialClub h) {
        // validar existencia de club y libro
        clubRepo.findById(h.getClub().getIdClub()).orElseThrow(() -> new RuntimeException("Club no encontrado"));
        libroRepo.findById(h.getLibro().getIdLibro()).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return historialRepo.save(h);
    }

    public HistorialClub actualizar(Integer id, HistorialClub actualizado) {
        HistorialClub h = historialRepo.findById(id).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        h.setMesAnio(actualizado.getMesAnio());
        h.setLibro(actualizado.getLibro());
        h.setClub(actualizado.getClub());
        return historialRepo.save(h);
    }

    public void eliminar(Integer id) {
        historialRepo.findById(id).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        historialRepo.deleteById(id);
    }
}
