package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Resena;
import com.utn.chapterone.repositories.ResenaRepository;

import java.util.List;

@Service
public class ResenaService {
    
    private final ResenaRepository resenaRepository;

    public ResenaService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    public List<Resena> obtenerTodos() {
        return resenaRepository.findAll();
    }

    public Resena obtenerPorId(Integer id) {
        return resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resena no encontrada"));
    }

    public Resena crear(Resena resena) {
        return resenaRepository.save(resena);
    }

    public Resena actualizar(Integer id, Resena resenaActualizada) {
        Resena resena = resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resena no encontrada"));

        resena.setContenido(resenaActualizada.getContenido());
        resena.setPuntaje(resenaActualizada.getPuntaje());
        resena.setFechaResena(resenaActualizada.getFechaResena());
        resena.setLectura(resenaActualizada.getLectura());

        return resenaRepository.save(resena);
    }

    public void eliminar(Integer id) {
        resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resena no encontrada"));

        resenaRepository.deleteById(id);
    }
}
