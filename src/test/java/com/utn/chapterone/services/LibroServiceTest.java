package com.utn.chapterone.services;

import com.utn.chapterone.dto.Libro.LibroOutputDTO;
import com.utn.chapterone.entities.Genero;
import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.AutorRepository;
import com.utn.chapterone.repositories.GeneroRepository;
import com.utn.chapterone.repositories.LecturaRepository;
import com.utn.chapterone.repositories.LibroRepository;
import com.utn.chapterone.repositories.SagaRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock private LibroRepository libroRepository;
    @Mock private LecturaRepository lecturaRepository;
    @Mock private AutorRepository autorRepository;
    @Mock private GeneroRepository generoRepository;
    @Mock private SagaRepository sagaRepository;
    @Mock private AuthorizationService authz;

    @InjectMocks
    private LibroService libroService;

    private Usuario usuario;
    private Genero fantasia;
    private Genero cienciaFiccion;
    private Genero terror;
    private Genero historia;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(7);
        usuario.setUsername("lector.prueba");

        fantasia = crearGenero(1, "Fantasía");
        cienciaFiccion = crearGenero(2, "Ciencia Ficción");
        terror = crearGenero(3, "Terror");
        historia = crearGenero(4, "Historia");
    }

    @Test
    void testObtenerRecomendadosExcluyeLeidosOrdenaPorAfinidadYValoracionYRespetaLimite() {
        when(authz.currentUserId()).thenReturn(7);

        // Libros que el usuario ya tiene en su lista (Fantasía aparece 2 veces, Ciencia Ficción 1 vez)
        Libro leido1 = crearLibro(1, "El Señor de los Anillos", List.of(fantasia));
        Libro leyendo2 = crearLibro(2, "Dune", List.of(cienciaFiccion, fantasia));

        Lectura lec1 = new Lectura(10, usuario, leido1, "leido", null, null);
        Lectura lec2 = new Lectura(11, usuario, leyendo2, "quiero leer", null, null);

        when(lecturaRepository.findByUsuario_IdUsuarioOrderByIdLecturaDesc(7))
                .thenReturn(List.of(lec1, lec2));

        // Libros candidatos en el catálogo:
        // - id 1 y 2 ya están en la lista del usuario (deben excluirse)
        // - id 3: Fantasía + Ciencia Ficción (afinidad = 2 + 1 = 3), valoración 4.2
        // - id 4: Fantasía (afinidad = 2), valoración 4.8
        // - id 5: Fantasía (afinidad = 2), valoración 3.9
        // - id 6: Ciencia Ficción (afinidad = 1), valoración 5.0
        // - id 7: Historia (afinidad = 0, debe excluirse)
        Libro cand3 = crearLibro(3, "Hyperion", List.of(fantasia, cienciaFiccion));
        Libro cand4 = crearLibro(4, "El Nombre del Viento", List.of(fantasia));
        Libro cand5 = crearLibro(5, "Mistborn", List.of(fantasia));
        Libro cand6 = crearLibro(6, "Fundación", List.of(cienciaFiccion));
        Libro cand7 = crearLibro(7, "Sapiens", List.of(historia));

        when(libroRepository.findAll())
                .thenReturn(List.of(leido1, leyendo2, cand3, cand4, cand5, cand6, cand7));

        when(lecturaRepository.obtenerValoracionPromedio(3)).thenReturn(4.2);
        when(lecturaRepository.obtenerValoracionPromedio(4)).thenReturn(4.8);
        when(lecturaRepository.obtenerValoracionPromedio(5)).thenReturn(3.9);
        when(lecturaRepository.obtenerValoracionPromedio(6)).thenReturn(5.0);

        // Reutilización 1: pedir solo 2 libros recomendados
        List<LibroOutputDTO> top2 = libroService.obtenerRecomendados(2);
        assertEquals(2, top2.size());
        assertEquals(3, top2.get(0).getIdLibro(), "Mayor afinidad (Fantasía + Ciencia Ficción = 3)");
        assertEquals(4, top2.get(1).getIdLibro(), "Segunda mayor afinidad (Fantasía = 2) y mayor valoración (4.8 vs 3.9)");

        // Reutilización 2: pedir hasta 10 libros recomendados
        List<LibroOutputDTO> todosRecomendados = libroService.obtenerRecomendados(10);
        assertEquals(4, todosRecomendados.size(), "No debe incluir libros ya en lista (1, 2) ni sin géneros en común (7)");
        assertEquals(List.of(3, 4, 5, 6), todosRecomendados.stream().map(LibroOutputDTO::getIdLibro).toList());
    }

    @Test
    void testObtenerRecomendadosLanzaUnauthorizedSiNoHayUsuarioAutenticado() {
        when(authz.currentUserId()).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> libroService.obtenerRecomendados(5));
    }

    @Test
    void testObtenerRecomendadosDevuelveVacioSiUsuarioNoTieneLecturas() {
        when(lecturaRepository.findByUsuario_IdUsuarioOrderByIdLecturaDesc(7))
                .thenReturn(List.of());

        Libro cand = crearLibro(3, "Hyperion", List.of(terror));
        List<LibroOutputDTO> res = libroService.obtenerRecomendadosParaUsuario(7, List.of(cand), 5);

        assertTrue(res.isEmpty());
    }

    private Genero crearGenero(int id, String nombre) {
        Genero g = new Genero();
        g.setIdGenero(id);
        g.setNombreGen(nombre);
        return g;
    }

    private Libro crearLibro(int id, String titulo, List<Genero> generos) {
        Libro l = new Libro();
        l.setIdLibro(id);
        l.setTitulo(titulo);
        l.setGeneros(generos);
        l.setAutores(List.of());
        return l;
    }
}
