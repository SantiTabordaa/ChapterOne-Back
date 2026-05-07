package com.utn.chapterone.services;

import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Integer id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public Libro crear(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro actualizar(Integer id, Libro libroActualizado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setSinopsis(libroActualizado.getSinopsis());
        libro.setSaga(libroActualizado.getSaga());
        libro.setNroTomo(libroActualizado.getNroTomo());
        libro.setCantPag(libroActualizado.getCantPag());
        libro.setValoracion(libroActualizado.getValoracion());
        libro.setAutores(libroActualizado.getAutores());
        libro.setGeneros(libroActualizado.getGeneros());
        
        return libroRepository.save(libro);
    }

    public void eliminar(Integer id) {
        libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        libroRepository.deleteById(id);
    }
}

