package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Saga;
import com.utn.chapterone.repositories.SagaRepository;

@Service
public class SagaService {
    
    private final SagaRepository sagaRepository;

    public SagaService(SagaRepository sagaRepository) {
        this.sagaRepository = sagaRepository;
    }

    public Saga guardar(Saga saga) {
        return sagaRepository.save(saga);
    }
}
