package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Solicitud;
import com.utn.chapterone.repositories.SolicitudRepository;

import java.util.List;

@Service
public class SolicitudService {
    
    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository){
        this.solicitudRepository = solicitudRepository;
    }

    public List<Solicitud> obtenerTodos() {
        return solicitudRepository.findAll();
    }

    public Solicitud obtenerPorId(Integer id) {
        return solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    public Solicitud crear(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public Solicitud actualizar(Integer id, Solicitud solicitudActualizada) {
        Solicitud solicitud = solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(solicitudActualizada.getEstado());
        solicitud.setFechaSolicitud(solicitudActualizada.getFechaSolicitud());
        solicitud.setFechaUnion(solicitudActualizada.getFechaUnion());
        solicitud.setRol(solicitudActualizada.getRol());
        solicitud.setUsuario(solicitudActualizada.getUsuario());
        solicitud.setClub(solicitudActualizada.getClub());

        return solicitudRepository.save(solicitud);
    }

    public void eliminar(Integer id) {
        solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitudRepository.deleteById(id);
    }
}

