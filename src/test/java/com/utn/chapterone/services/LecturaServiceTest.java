package com.utn.chapterone.services;

import com.utn.chapterone.dto.Libro.LibroOutputDTO;
import com.utn.chapterone.dto.lectura.LecturaInputDTO;
import com.utn.chapterone.dto.lectura.LecturaOutputDTO;
import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.LecturaRepository;
import com.utn.chapterone.repositories.LibroRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturaServiceTest {

    @Mock private LecturaRepository lecturaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private LibroRepository libroRepository;
    @Mock private LibroService libroService;
    @Mock private AuthorizationService authz;

    @InjectMocks
    private LecturaService lecturaService;

    private Usuario usuario;
    private Libro libro;
    private LibroOutputDTO libroOutputDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(4);
        usuario.setUsername("diego.club");

        libro = new Libro();
        libro.setIdLibro(10);
        libro.setTitulo("Cien años de soledad");

        libroOutputDTO = new LibroOutputDTO();
        libroOutputDTO.setIdLibro(10);
        libroOutputDTO.setTitulo("Cien años de soledad");
    }

    @Test
    void testObtenerMisLecturas() {
        when(authz.currentUserId()).thenReturn(4);

        Lectura lectura = new Lectura();
        lectura.setIdLectura(100);
        lectura.setUsuario(usuario);
        lectura.setLibro(libro);
        lectura.setEstado("quiero leer");

        when(lecturaRepository.findByUsuario_IdUsuarioOrderByIdLecturaDesc(4))
                .thenReturn(List.of(lectura));
        when(libroService.toOutputDTO(libro)).thenReturn(libroOutputDTO);

        List<LecturaOutputDTO> resultado = lecturaService.obtenerMisLecturas();

        assertEquals(1, resultado.size());
        assertEquals(100, resultado.get(0).getIdLectura());
        assertEquals(4, resultado.get(0).getIdUsuario());
        assertEquals("quiero leer", resultado.get(0).getEstado());
        assertEquals("Cien años de soledad", resultado.get(0).getLibro().getTitulo());
    }

    @Test
    void testCrearNuevaLecturaAsignaFechasAutomaticamente() {
        when(authz.currentUserId()).thenReturn(4);
        when(authz.isAdmin()).thenReturn(false);
        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(4, 10))
                .thenReturn(Optional.empty());
        when(libroService.toOutputDTO(libro)).thenReturn(libroOutputDTO);

        when(lecturaRepository.save(any(Lectura.class))).thenAnswer(inv -> {
            Lectura l = inv.getArgument(0);
            l.setIdLectura(55);
            return l;
        });

        LecturaInputDTO input = new LecturaInputDTO();
        input.setIdLibro(10);
        input.setEstado("leído"); // con tilde, debe normalizarse a "leido"

        LecturaOutputDTO out = lecturaService.crear(input);

        assertEquals(55, out.getIdLectura());
        assertEquals("leido", out.getEstado());
        assertNotNull(out.getFechaIni(), "Al marcar como leído debe asignarse fechaIni");
        assertNotNull(out.getFechaFin(), "Al marcar como leído debe asignarse fechaFin");
    }

    @Test
    void testCrearHaceUpsertSiYaExisteElLibroEnReadlist() {
        when(authz.currentUserId()).thenReturn(4);
        when(authz.isAdmin()).thenReturn(false);
        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        when(libroService.toOutputDTO(libro)).thenReturn(libroOutputDTO);

        Lectura existente = new Lectura();
        existente.setIdLectura(77);
        existente.setUsuario(usuario);
        existente.setLibro(libro);
        existente.setEstado("quiero leer");

        when(lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(4, 10))
                .thenReturn(Optional.of(existente));
        when(lecturaRepository.save(any(Lectura.class))).thenAnswer(inv -> inv.getArgument(0));

        LecturaInputDTO input = new LecturaInputDTO();
        input.setIdLibro(10);
        input.setEstado("leyendo");

        LecturaOutputDTO out = lecturaService.crear(input);

        assertEquals(77, out.getIdLectura(), "Debe reutilizar el idLectura existente en vez de duplicar");
        assertEquals("leyendo", out.getEstado());
        assertNotNull(out.getFechaIni());
        assertNull(out.getFechaFin());
    }
}
