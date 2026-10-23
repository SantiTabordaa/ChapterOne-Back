package com.utn.chapterone.services;

import com.utn.chapterone.dto.resena.ResenaInputDTO;
import com.utn.chapterone.dto.resena.ResenaOutputDTO;
import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.entities.Resena;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.LecturaRepository;
import com.utn.chapterone.repositories.LibroRepository;
import com.utn.chapterone.repositories.ResenaRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock private ResenaRepository resenaRepository;
    @Mock private LecturaRepository lecturaRepository;
    @Mock private LibroRepository libroRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private AuthorizationService authz;

    @InjectMocks
    private ResenaService resenaService;

    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(4);
        usuario.setUsername("diego.club");
        usuario.setNombre("Diego");
        usuario.setApellido("Fernandez");

        libro = new Libro();
        libro.setIdLibro(10);
        libro.setTitulo("Cien años de soledad");
    }

    @Test
    void testCrearResenaSinLecturaPreviaCreaLecturaLeido() {
        when(authz.currentUserId()).thenReturn(4);
        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(4, 10))
                .thenReturn(Optional.empty());

        when(lecturaRepository.save(any(Lectura.class))).thenAnswer(inv -> {
            Lectura l = inv.getArgument(0);
            l.setIdLectura(88);
            return l;
        });
        when(resenaRepository.findByLectura_IdLectura(88)).thenReturn(Optional.empty());
        when(resenaRepository.save(any(Resena.class))).thenAnswer(inv -> {
            Resena r = inv.getArgument(0);
            r.setIdResena(15);
            return r;
        });

        ResenaInputDTO input = new ResenaInputDTO();
        input.setIdLibro(10);
        input.setPuntaje(4.5);
        input.setContenido("Una obra maestra absoluta.");

        ResenaOutputDTO out = resenaService.crear(input);

        assertEquals(15, out.getIdResena());
        assertEquals(88, out.getIdLectura());
        assertEquals(10, out.getIdLibro());
        assertEquals(4, out.getIdUsuario());
        assertEquals("diego.club", out.getUsername());
        assertEquals(4.5, out.getPuntaje());
        assertEquals("Una obra maestra absoluta.", out.getContenido());
        assertNotNull(out.getFechaResena());
    }

    @Test
    void testCrearResenaActualizaSiYaExisteParaEsaLectura() {
        when(authz.currentUserId()).thenReturn(4);
        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));

        Lectura lecturaExistente = new Lectura();
        lecturaExistente.setIdLectura(90);
        lecturaExistente.setUsuario(usuario);
        lecturaExistente.setLibro(libro);
        lecturaExistente.setEstado("quiero leer");

        Resena resenaExistente = new Resena();
        resenaExistente.setIdResena(20);
        resenaExistente.setLectura(lecturaExistente);
        resenaExistente.setPuntaje(3.0);
        resenaExistente.setContenido("Primera impresión");

        when(lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(4, 10))
                .thenReturn(Optional.of(lecturaExistente));
        when(lecturaRepository.save(any(Lectura.class))).thenAnswer(inv -> inv.getArgument(0));
        when(resenaRepository.findByLectura_IdLectura(90)).thenReturn(Optional.of(resenaExistente));
        when(resenaRepository.save(any(Resena.class))).thenAnswer(inv -> inv.getArgument(0));

        ResenaInputDTO input = new ResenaInputDTO();
        input.setIdLibro(10);
        input.setPuntaje(5.0);
        input.setContenido("Relectura increíble.");

        ResenaOutputDTO out = resenaService.crear(input);

        assertEquals(20, out.getIdResena());
        assertEquals("leido", lecturaExistente.getEstado());
        assertEquals(5.0, out.getPuntaje());
        assertEquals("Relectura increíble.", out.getContenido());
    }

    @Test
    void testValidacionPuntajeFueraDeRangoLanzaExcepcion() {
        when(authz.currentUserId()).thenReturn(4);

        ResenaInputDTO input = new ResenaInputDTO();
        input.setIdLibro(10);
        input.setPuntaje(6.0);
        input.setContenido("Puntaje inválido");

        assertThrows(ResponseStatusException.class, () -> resenaService.crear(input));
    }

    @Test
    void testObtenerPorLibroMapeaDatosDeUsuarioYLibro() {
        Lectura lectura = new Lectura();
        lectura.setIdLectura(90);
        lectura.setUsuario(usuario);
        lectura.setLibro(libro);

        Resena resena = new Resena();
        resena.setIdResena(25);
        resena.setLectura(lectura);
        resena.setPuntaje(4.8);
        resena.setContenido("Excelente ritmo.");
        resena.setFechaResena(LocalDate.of(2026, 10, 16));

        when(resenaRepository.findByLectura_Libro_IdLibroOrderByFechaResenaDescIdResenaDesc(10))
                .thenReturn(List.of(resena));

        List<ResenaOutputDTO> lista = resenaService.obtenerPorLibro(10);

        assertEquals(1, lista.size());
        assertEquals("Diego", lista.get(0).getNombreUsuario());
        assertEquals("Fernandez", lista.get(0).getApellidoUsuario());
        assertEquals("diego.club", lista.get(0).getUsername());
        assertEquals("Cien años de soledad", lista.get(0).getTituloLibro());
    }

    @Test
    void testCrearResenaSoloConPuntajeSinContenido() {
        when(authz.currentUserId()).thenReturn(4);
        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(4, 10))
                .thenReturn(Optional.empty());
        when(lecturaRepository.save(any(Lectura.class))).thenAnswer(inv -> {
            Lectura l = inv.getArgument(0);
            l.setIdLectura(95);
            return l;
        });
        when(resenaRepository.findByLectura_IdLectura(95)).thenReturn(Optional.empty());
        when(resenaRepository.save(any(Resena.class))).thenAnswer(inv -> {
            Resena r = inv.getArgument(0);
            r.setIdResena(30);
            return r;
        });

        ResenaInputDTO input = new ResenaInputDTO();
        input.setIdLibro(10);
        input.setPuntaje(4.0);
        input.setContenido(null);

        ResenaOutputDTO out = resenaService.crear(input);

        assertEquals(30, out.getIdResena());
        assertEquals(4.0, out.getPuntaje());
        assertEquals("", out.getContenido());
    }

    @Test
    void testFechaResenaFuturaLanzaExcepcion() {
        when(authz.currentUserId()).thenReturn(4);

        ResenaInputDTO input = new ResenaInputDTO();
        input.setIdLibro(10);
        input.setPuntaje(4.5);
        input.setFechaResena(LocalDate.now().plusDays(1));

        assertThrows(ResponseStatusException.class, () -> resenaService.crear(input));
    }
}
