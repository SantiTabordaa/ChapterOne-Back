package com.utn.chapterone.services;

import com.utn.chapterone.entities.Genero;
import com.utn.chapterone.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    // Obtener todos los géneros
    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    // Obtener un género por ID
    public Genero obtenerPorId(Integer id) {
        return generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
    }

    // Crear un nuevo género
    public Genero crear(Genero genero) {
        return generoRepository.save(genero);
    }

    // Actualizar un género
    public Genero actualizar(Integer id, Genero generoActualizado) {
        Genero genero = generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        
        genero.setNombreGen(generoActualizado.getNombreGen());
        
        return generoRepository.save(genero);
    }

    // Eliminar un género (con validación de clubes)
    public void eliminarGenero(Integer idGenero) {
        Genero genero = generoRepository.findById(idGenero)
            .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        
        // Validar que no tenga clubes asociados
        if (genero.getClubes() != null && !genero.getClubes().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un género que tiene clubes asociados");
        }
        
        generoRepository.deleteById(idGenero);
    }
}
