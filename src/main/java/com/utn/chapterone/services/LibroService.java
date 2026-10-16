package com.utn.chapterone.services;

import com.utn.chapterone.dto.autor.AutorDTO;
import com.utn.chapterone.dto.Libro.LibroInputDTO;
import com.utn.chapterone.dto.Libro.LibroOutputDTO;
import com.utn.chapterone.entities.*;
import com.utn.chapterone.repositories.*;
import com.utn.chapterone.security.AuthorizationService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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

    @Autowired
    private AuthorizationService authz;

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
    public List<LibroOutputDTO> obtenerRecomendados(int limite) {
        Integer uid = authz.currentUserId();
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
        return obtenerRecomendadosParaUsuario(uid, limite);
    }

    @Transactional
    public List<LibroOutputDTO> obtenerRecomendadosParaUsuario(Integer idUsuario, int limite) {
        return obtenerRecomendadosParaUsuario(idUsuario, libroRepository.findAll(), limite);
    }

    @Transactional
    public List<LibroOutputDTO> obtenerRecomendadosParaUsuario(
            Integer idUsuario,
            List<Libro> librosCandidatos,
            int limite
    ) {
        if (idUsuario == null || limite <= 0 || librosCandidatos == null || librosCandidatos.isEmpty()) {
            return List.of();
        }

        List<Lectura> lecturasUsuario = lecturaRepository.findByUsuario_IdUsuarioOrderByIdLecturaDesc(idUsuario);
        if (lecturasUsuario == null || lecturasUsuario.isEmpty()) {
            return List.of();
        }

        Set<Integer> librosYaEnLista = lecturasUsuario.stream()
                .map(Lectura::getLibro)
                .filter(Objects::nonNull)
                .map(Libro::getIdLibro)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, Long> frecuenciaGeneros = lecturasUsuario.stream()
                .map(Lectura::getLibro)
                .filter(Objects::nonNull)
                .filter(libro -> libro.getGeneros() != null)
                .flatMap(libro -> libro.getGeneros().stream())
                .map(this::claveGenero)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(clave -> clave, Collectors.counting()));

        if (frecuenciaGeneros.isEmpty()) {
            return List.of();
        }

        record LibroConAfinidad(LibroOutputDTO dto, long afinidad) {}

        return librosCandidatos.stream()
                .filter(Objects::nonNull)
                .filter(libro -> libro.getIdLibro() == null || !librosYaEnLista.contains(libro.getIdLibro()))
                .map(libro -> {
                    long afinidad = calcularAfinidad(libro, frecuenciaGeneros);
                    if (afinidad <= 0) {
                        return null;
                    }
                    return new LibroConAfinidad(toOutputDTO(libro), afinidad);
                })
                .filter(Objects::nonNull)
                .sorted(Comparator
                        .comparingLong(LibroConAfinidad::afinidad).reversed()
                        .thenComparing(
                                item -> item.dto().getValoracion() != null ? item.dto().getValoracion() : 0.0,
                                Comparator.reverseOrder()
                        ))
                .limit(limite)
                .map(LibroConAfinidad::dto)
                .collect(Collectors.toList());
    }

    private long calcularAfinidad(Libro libro, Map<String, Long> frecuenciaGeneros) {
        if (libro.getGeneros() == null || libro.getGeneros().isEmpty()) {
            return 0L;
        }
        return libro.getGeneros().stream()
                .map(this::claveGenero)
                .filter(Objects::nonNull)
                .distinct()
                .mapToLong(clave -> frecuenciaGeneros.getOrDefault(clave, 0L))
                .sum();
    }

    private String claveGenero(Genero genero) {
        if (genero == null) {
            return null;
        }
        if (genero.getIdGenero() != null) {
            return "ID:" + genero.getIdGenero();
        }
        if (genero.getNombreGen() != null && !genero.getNombreGen().isBlank()) {
            return "NOMBRE:" + genero.getNombreGen().trim().toLowerCase();
        }
        return null;
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

    public LibroOutputDTO toOutputDTO(Libro libro) {
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

        dto.setAutores(libro.getAutores() == null ? List.of() : libro.getAutores().stream().map(autor -> {
            AutorDTO autorDTO = new AutorDTO();
            autorDTO.setIdAutor(autor.getIdAutor());
            autorDTO.setNombre(autor.getNombre());
            autorDTO.setApellido(autor.getApellido());
            return autorDTO;
        }).collect(Collectors.toList()));

        dto.setGeneros(libro.getGeneros() == null ? List.of() : libro.getGeneros().stream()
                .map(Genero::getNombreGen)
                .collect(Collectors.toList()));

        return dto;
    }
}

