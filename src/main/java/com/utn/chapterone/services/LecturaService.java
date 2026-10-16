package com.utn.chapterone.services;

import com.utn.chapterone.dto.lectura.LecturaInputDTO;
import com.utn.chapterone.dto.lectura.LecturaOutputDTO;
import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.entities.Libro;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.LecturaRepository;
import com.utn.chapterone.repositories.LibroRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LecturaService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("quiero leer", "leyendo", "leido");

    private final LecturaRepository lecturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final LibroService libroService;
    private final AuthorizationService authz;

    public LecturaService(
            LecturaRepository lecturaRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            LibroService libroService,
            AuthorizationService authz
    ) {
        this.lecturaRepository = lecturaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.libroService = libroService;
        this.authz = authz;
    }

    @Transactional
    public List<LecturaOutputDTO> obtenerTodos() {
        return lecturaRepository.findAll().stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LecturaOutputDTO> obtenerMisLecturas() {
        Integer uid = authz.currentUserId();
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
        return lecturaRepository.findByUsuario_IdUsuarioOrderByIdLecturaDesc(uid).stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<LecturaOutputDTO> obtenerMiLecturaPorLibro(Integer idLibro) {
        Integer uid = authz.currentUserId();
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
        return lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(uid, idLibro)
                .map(this::toOutputDTO);
    }

    @Transactional
    public LecturaOutputDTO obtenerPorId(Integer id) {
        Lectura lectura = lecturaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lectura no encontrada"));
        return toOutputDTO(lectura);
    }

    @Transactional
    public LecturaOutputDTO crear(LecturaInputDTO dto) {
        Integer uid = authz.currentUserId();
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        Integer targetUserId = (authz.isAdmin() && dto.getIdUsuario() != null) ? dto.getIdUsuario() : uid;
        Usuario usuario = usuarioRepository.findById(targetUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Integer idLibro = dto.getIdLibro();
        if (idLibro == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar el libro (idLibro)");
        }

        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        String estadoNormalizado = normalizarEstado(dto.getEstado());

        // Si el usuario ya tiene este libro en su lista de lectura, actualizamos esa lectura (upsert)
        Optional<Lectura> existenteOpt = lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(targetUserId, idLibro);
        Lectura lectura = existenteOpt.orElseGet(() -> {
            Lectura nueva = new Lectura();
            nueva.setUsuario(usuario);
            nueva.setLibro(libro);
            return nueva;
        });

        aplicarEstadoYFechas(lectura, estadoNormalizado, dto.getFechaIni(), dto.getFechaFin());

        Lectura guardada = lecturaRepository.save(lectura);
        return toOutputDTO(guardada);
    }

    @Transactional
    public LecturaOutputDTO actualizar(Integer id, LecturaInputDTO dto) {
        Lectura lectura = lecturaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lectura no encontrada"));

        Integer idLibro = dto.getIdLibro();
        if (idLibro != null) {
            Libro libro = libroRepository.findById(idLibro)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
            lectura.setLibro(libro);
        }

        if (authz.isAdmin() && dto.getIdUsuario() != null) {
            usuarioRepository.findById(dto.getIdUsuario()).ifPresent(lectura::setUsuario);
        }

        String estadoNormalizado = dto.getEstado() != null
                ? normalizarEstado(dto.getEstado())
                : normalizarEstado(lectura.getEstado());

        aplicarEstadoYFechas(lectura, estadoNormalizado, dto.getFechaIni(), dto.getFechaFin());

        Lectura guardada = lecturaRepository.save(lectura);
        return toOutputDTO(guardada);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!lecturaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lectura no encontrada");
        }
        lecturaRepository.deleteById(id);
    }

    private String normalizarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return "quiero leer";
        }
        String clean = estado.trim().toLowerCase()
                .replace("leído", "leido");
        if (!ESTADOS_VALIDOS.contains(clean)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Estado de lectura inválido. Valores permitidos: 'quiero leer', 'leyendo', 'leido'"
            );
        }
        return clean;
    }

    private void aplicarEstadoYFechas(Lectura lectura, String estado, Date fechaIniInput, Date fechaFinInput) {
        lectura.setEstado(estado);
        Date ahora = new Date();

        switch (estado) {
            case "quiero leer" -> {
                lectura.setFechaIni(fechaIniInput);
                lectura.setFechaFin(null);
            }
            case "leyendo" -> {
                if (fechaIniInput != null) {
                    lectura.setFechaIni(fechaIniInput);
                } else if (lectura.getFechaIni() == null) {
                    lectura.setFechaIni(ahora);
                }
                lectura.setFechaFin(null);
            }
            case "leido" -> {
                if (fechaIniInput != null) {
                    lectura.setFechaIni(fechaIniInput);
                } else if (lectura.getFechaIni() == null) {
                    lectura.setFechaIni(ahora);
                }
                if (fechaFinInput != null) {
                    lectura.setFechaFin(fechaFinInput);
                } else if (lectura.getFechaFin() == null) {
                    lectura.setFechaFin(ahora);
                }
            }
        }
    }

    public LecturaOutputDTO toOutputDTO(Lectura lectura) {
        LecturaOutputDTO dto = new LecturaOutputDTO();
        dto.setIdLectura(lectura.getIdLectura());
        if (lectura.getUsuario() != null) {
            dto.setIdUsuario(lectura.getUsuario().getIdUsuario());
            dto.setUsername(lectura.getUsuario().getUsername());
        }
        if (lectura.getLibro() != null) {
            dto.setLibro(libroService.toOutputDTO(lectura.getLibro()));
        }
        dto.setEstado(lectura.getEstado());
        dto.setFechaIni(lectura.getFechaIni());
        dto.setFechaFin(lectura.getFechaFin());
        dto.setResena(lectura.getResena());
        return dto;
    }
}
