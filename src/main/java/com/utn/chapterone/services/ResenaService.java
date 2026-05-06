package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Resena;
import com.utn.chapterone.repositories.ResenaRepository;

@Service
public class ResenaService {
    
    private final ResenaRepository resenaRepository;

    public ResenaService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    public Resena guardar(Resena resena) {
        return resenaRepository.save(resena);
    }
}
