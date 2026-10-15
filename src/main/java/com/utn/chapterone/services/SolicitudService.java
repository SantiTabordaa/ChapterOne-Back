package com.utn.chapterone.services;

import com.utn.chapterone.entities.Solicitud;
import com.utn.chapterone.repositories.SolicitudRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authz;

    public SolicitudService(
            SolicitudRepository solicitudRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authz
    ) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.authz = authz;
    }

    public List<Solicitud> obtenerTodos() {
        return solicitudRepository.findAll();
    }

    public Solicitud obtenerPorId(Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
    }

    public Solicitud crear(Solicitud solicitud) {
        Integer uid = authz.currentUserId();
        if (uid != null) {
            usuarioRepository.findById(uid).ifPresent(solicitud::setUsuario);
        }
        if (solicitud.getFechaSolicitud() == null) {
            solicitud.setFechaSolicitud(Date.valueOf(LocalDate.now()));
        }
        if (solicitud.getEstado() == null || solicitud.getEstado().isBlank()) {
            solicitud.setEstado("pendiente");
        }
        return solicitudRepository.save(solicitud);
    }

    public Solicitud actualizar(Integer id, Solicitud solicitudActualizada) {
        Solicitud solicitud = obtenerPorId(id);

        solicitud.setEstado(solicitudActualizada.getEstado());
        solicitud.setFechaSolicitud(solicitudActualizada.getFechaSolicitud());
        solicitud.setFechaUnion(solicitudActualizada.getFechaUnion());
        solicitud.setRol(solicitudActualizada.getRol());

        if (authz.isAdmin()) {
            if (solicitudActualizada.getUsuario() != null) {
                solicitud.setUsuario(solicitudActualizada.getUsuario());
            }
            if (solicitudActualizada.getClub() != null) {
                solicitud.setClub(solicitudActualizada.getClub());
            }
        }

        return solicitudRepository.save(solicitud);
    }

    public void eliminar(Integer id) {
        Solicitud solicitud = obtenerPorId(id);
        solicitudRepository.delete(solicitud);
    }
}
