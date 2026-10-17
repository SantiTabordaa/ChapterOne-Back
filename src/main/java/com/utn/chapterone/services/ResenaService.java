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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final LecturaRepository lecturaRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authz;

    public ResenaService(
            ResenaRepository resenaRepository,
            LecturaRepository lecturaRepository,
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authz
    ) {
        this.resenaRepository = resenaRepository;
        this.lecturaRepository = lecturaRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.authz = authz;
    }

    @Transactional
    public List<ResenaOutputDTO> obtenerTodos() {
        return resenaRepository.findAll().stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResenaOutputDTO obtenerPorId(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
        return toOutputDTO(resena);
    }

    @Transactional
    public List<ResenaOutputDTO> obtenerPorLibro(Integer idLibro) {
        return resenaRepository.findByLectura_Libro_IdLibroOrderByFechaResenaDescIdResenaDesc(idLibro).stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResenaOutputDTO crear(ResenaInputDTO dto) {
        Integer uid = authz.currentUserId();
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        validarDatosResena(dto);

        Lectura lectura = resolverLecturaParaResena(uid, dto);

        // Si ya existe una reseña para esta lectura (relación 1 a 1), la actualizamos (upsert)
        Optional<Resena> existenteOpt = resenaRepository.findByLectura_IdLectura(lectura.getIdLectura());
        Resena resena = existenteOpt.orElseGet(() -> {
            Resena nueva = new Resena();
            nueva.setLectura(lectura);
            lectura.setResena(nueva);
            return nueva;
        });

        String contenidoNormalizado = dto.getContenido() != null ? dto.getContenido().trim() : "";
        resena.setContenido(contenidoNormalizado);
        resena.setPuntaje(dto.getPuntaje());
        resena.setFechaResena(dto.getFechaResena() != null ? dto.getFechaResena() : LocalDate.now());

        Resena guardada = resenaRepository.save(resena);
        return toOutputDTO(guardada);
    }

    @Transactional
    public ResenaOutputDTO actualizar(Integer id, ResenaInputDTO dto) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));

        validarDatosResena(dto);

        String contenidoNormalizado = dto.getContenido() != null ? dto.getContenido().trim() : "";
        resena.setContenido(contenidoNormalizado);
        resena.setPuntaje(dto.getPuntaje());
        resena.setFechaResena(dto.getFechaResena() != null ? dto.getFechaResena() : LocalDate.now());

        Resena guardada = resenaRepository.save(resena);
        return toOutputDTO(guardada);
    }

    @Transactional
    public void eliminar(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
        Lectura lectura = resena.getLectura();
        if (lectura != null) {
            lectura.setResena(null);
            lecturaRepository.save(lectura);
        }
        resenaRepository.delete(resena);
    }

    private void validarDatosResena(ResenaInputDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos de la reseña son obligatorios");
        }
        if (dto.getPuntaje() == null || dto.getPuntaje() < 1.0 || dto.getPuntaje() > 5.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El puntaje debe estar entre 1.0 y 5.0");
        }
        if (dto.getFechaResena() != null && dto.getFechaResena().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la reseña no puede ser futura");
        }
    }

    private Lectura resolverLecturaParaResena(Integer uid, ResenaInputDTO dto) {
        Date fechaReferencia = dto.getFechaResena() != null
                ? java.sql.Date.valueOf(dto.getFechaResena())
                : new Date();

        if (dto.getIdLectura() != null) {
            if (!authz.ownsLectura(dto.getIdLectura())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes crear una reseña para una lectura ajena.");
            }
            Lectura lectura = lecturaRepository.findById(dto.getIdLectura())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lectura no encontrada"));
            marcarComoLeido(lectura, fechaReferencia);
            return lecturaRepository.save(lectura);
        }

        if (dto.getIdLibro() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar idLibro o idLectura para crear la reseña");
        }

        Usuario usuario = usuarioRepository.findById(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        Lectura lectura = lecturaRepository.findByUsuario_IdUsuarioAndLibro_IdLibro(uid, dto.getIdLibro())
                .orElseGet(() -> {
                    Lectura nueva = new Lectura();
                    nueva.setUsuario(usuario);
                    nueva.setLibro(libro);
                    return nueva;
                });

        marcarComoLeido(lectura, fechaReferencia);
        return lecturaRepository.save(lectura);
    }

    private void marcarComoLeido(Lectura lectura, Date fechaReferencia) {
        lectura.setEstado("leido");
        if (lectura.getFechaIni() == null) {
            lectura.setFechaIni(fechaReferencia);
        }
        if (lectura.getFechaFin() == null) {
            lectura.setFechaFin(fechaReferencia);
        }
    }

    public ResenaOutputDTO toOutputDTO(Resena resena) {
        ResenaOutputDTO dto = new ResenaOutputDTO();
        dto.setIdResena(resena.getIdResena());
        dto.setContenido(resena.getContenido());
        dto.setPuntaje(resena.getPuntaje());
        dto.setFechaResena(resena.getFechaResena());

        Lectura lectura = resena.getLectura();
        if (lectura != null) {
            dto.setIdLectura(lectura.getIdLectura());
            if (lectura.getLibro() != null) {
                dto.setIdLibro(lectura.getLibro().getIdLibro());
                dto.setTituloLibro(lectura.getLibro().getTitulo());
            }
            if (lectura.getUsuario() != null) {
                Usuario u = lectura.getUsuario();
                dto.setIdUsuario(u.getIdUsuario());
                dto.setUsername(u.getUsername());
                dto.setNombreUsuario(u.getNombre());
                dto.setApellidoUsuario(u.getApellido());
                dto.setUrlFotoPerfil(u.getUrlFotoPerfil());
            }
        }

        return dto;
    }
}
