package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Solicitud;
import com.utn.chapterone.repositories.SolicitudRepository;

@Service
public class SolicitudService {
    
    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository){
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud guardar(Solicitud solicitud){
        return solicitudRepository.save(solicitud);
    }
}

