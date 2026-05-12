package com.utn.chapterone.services;

import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.repositories.LibroRepository;
import com.utn.chapterone.repositories.LecturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LecturaRepository lecturaRepository;

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }
    public Libro obtenerLibroConValoracion(Integer idLibro){

        Optional<Libro> libroOpt = libroRepository.findById(idLibro);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            //Le pedimos a la BD que calcule el promedio 
            Double valoracion = lecturaRepository.obtenerValoracionPromedio(idLibro);
            if(valoracion == null){
                valoracion=0.0;
            }
            libro.setValoracion(Math.round(valoracion * 100.0) / 100.0);
            return libro;
        }
        throw new RuntimeException("Libro no encontrado");
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
        libro.setUrlFotoPortada(libroActualizado.getUrlFotoPortada());
        
        return libroRepository.save(libro);
    }

    public void eliminar(Integer id) {
        libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        libroRepository.deleteById(id);
    }
}

