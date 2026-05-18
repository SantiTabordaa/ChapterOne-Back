package com.utn.chapterone.services;

import com.utn.chapterone.dto.autor.AutorDTO;
import com.utn.chapterone.dto.Libro.LibroInputDTO;
import com.utn.chapterone.dto.Libro.LibroOutputDTO;
import com.utn.chapterone.entities.*;
import com.utn.chapterone.repositories.*;


import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LecturaRepository lecturaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private SagaRepository sagaRepository;

    public List<LibroOutputDTO> getAll() {
        return libroRepository.findAll().stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    public LibroOutputDTO getById(Integer id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return toOutputDTO(libro);
    }

    @Transactional
    public LibroOutputDTO save(LibroInputDTO libroInputDTO) {
        Libro libro = new Libro();
        libro.setTitulo(libroInputDTO.getTitulo());
        libro.setSinopsis(libroInputDTO.getSinopsis());
        libro.setNroTomo(libroInputDTO.getNroTomo());
        libro.setCantPag(libroInputDTO.getCantPag());
        libro.setUrlPortada(libroInputDTO.getUrlPortada());
        // Manejo de Autores
        List<Autor> autores = new ArrayList<>();
        for (Integer autorId : libroInputDTO.getAutoresIds()) {
            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + autorId));
            autores.add(autor);
        }
        libro.setAutores(autores);

        // Manejo de Géneros
        List<Genero> generos = new ArrayList<>();
        for (Integer generoId : libroInputDTO.getGenerosIds()) {
            Genero genero = generoRepository.findById(generoId)
                    .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + generoId));
            generos.add(genero);
        }
        libro.setGeneros(generos);

        // Manejo de Saga
        if (libroInputDTO.getSaga() != null && !libroInputDTO.getSaga().isEmpty()) {
            Saga saga = sagaRepository.findByNombre(libroInputDTO.getSaga())
                    .orElseThrow(() -> new RuntimeException("Saga no encontrada: " + libroInputDTO.getSaga()));
            libro.setSaga(saga);
        }

        Libro libroGuardado = libroRepository.save(libro);
        return toOutputDTO(libroGuardado);
    }

    @Transactional
    public LibroOutputDTO update(Integer id, LibroInputDTO libroInputDTO) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        libro.setTitulo(libroInputDTO.getTitulo());
        libro.setSinopsis(libroInputDTO.getSinopsis());
        libro.setNroTomo(libroInputDTO.getNroTomo());
        libro.setCantPag(libroInputDTO.getCantPag());
        libro.setUrlPortada(libroInputDTO.getUrlPortada());
        

        // Actualización de Autores
        List<Autor> autores = new ArrayList<>();
        for (Integer autorId : libroInputDTO.getAutoresIds()) {
            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + autorId));
            autores.add(autor);
        }
        libro.setAutores(autores);

        // Actualización de Géneros
        List<Genero> generos = new ArrayList<>();
        for (Integer generoId : libroInputDTO.getGenerosIds()) {
            Genero genero = generoRepository.findById(generoId)
                    .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + generoId));
            generos.add(genero);
        }
        libro.setGeneros(generos);

        // Actualización de Saga
        if (libroInputDTO.getSaga() != null && !libroInputDTO.getSaga().isEmpty()) {
            Saga saga = sagaRepository.findByNombre(libroInputDTO.getSaga())
                    .orElseThrow(() -> new RuntimeException("Saga no encontrada: " + libroInputDTO.getSaga()));
            libro.setSaga(saga);
        } else {
            libro.setSaga(null);
        }

        Libro libroActualizado = libroRepository.save(libro);
        return toOutputDTO(libroActualizado);
    }

    public void delete(Integer id) {
        libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libroRepository.deleteById(id);
    }

    private LibroOutputDTO toOutputDTO(Libro libro) {
        LibroOutputDTO dto = new LibroOutputDTO();
        dto.setIdLibro(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setSinopsis(libro.getSinopsis());
        dto.setNroTomo(libro.getNroTomo());
        dto.setCantPag(libro.getCantPag());
        dto.setUrlPortada(libro.getUrlPortada());
        Double valoracion = lecturaRepository.obtenerValoracionPromedio(libro.getIdLibro());
        dto.setValoracion(valoracion != null ? Math.round(valoracion * 100.0) / 100.0 : 0.0);

        if (libro.getSaga() != null) {
            dto.setSaga(libro.getSaga().getNombre());
        }

        dto.setAutores(libro.getAutores().stream().map(autor -> {
            AutorDTO autorDTO = new AutorDTO();
            autorDTO.setIdAutor(autor.getIdAutor());
            autorDTO.setNombre(autor.getNombre());
            autorDTO.setApellido(autor.getApellido());
            return autorDTO;
        }).collect(Collectors.toList()));

        dto.setGeneros(libro.getGeneros().stream()
                .map(Genero::getNombreGen)
                .collect(Collectors.toList()));

        return dto;
    }
}

