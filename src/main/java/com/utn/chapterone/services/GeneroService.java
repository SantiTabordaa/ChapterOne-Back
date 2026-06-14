package com.utn.chapterone.services;

import com.utn.chapterone.entities.Genero;
import com.utn.chapterone.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.utn.chapterone.dto.genero.GeneroDTO;


import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    public Genero obtenerPorId(Integer id) {
        return generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
    }

    public Genero crear(Genero genero) {
        return generoRepository.save(genero);
    }

    public Genero actualizar(Integer id, Genero generoActualizado) {
        Genero genero = generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        
        genero.setNombreGen(generoActualizado.getNombreGen());
        
        return generoRepository.save(genero);
    }

    public void eliminarGenero(Integer idGenero) {
        Genero genero = generoRepository.findById(idGenero)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        
        if (genero.getClubes() != null && !genero.getClubes().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un género que tiene clubes asociados");
        }
        
        generoRepository.deleteById(idGenero);
    }

    public List<GeneroDTO> obtenerTodosDTO() {
    return generoRepository.findAll().stream()
        .map(g -> new GeneroDTO(g.getIdGenero(), g.getNombreGen()))
        .toList();
}
}
