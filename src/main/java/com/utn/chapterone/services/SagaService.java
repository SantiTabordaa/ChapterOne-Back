package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Saga;
import com.utn.chapterone.repositories.SagaRepository;

import java.util.List;

@Service
public class SagaService {
    
    private final SagaRepository sagaRepository;

    public SagaService(SagaRepository sagaRepository) {
        this.sagaRepository = sagaRepository;
    }

    public List<Saga> obtenerTodos() {
        return sagaRepository.findAll();
    }

    public Saga obtenerPorId(Integer id) {
        return sagaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Saga no encontrada"));
    }

    public Saga crear(Saga saga) {
        return sagaRepository.save(saga);
    }

    public Saga actualizar(Integer id, Saga sagaActualizada) {
        Saga saga = sagaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Saga no encontrada"));

        saga.setNombre(sagaActualizada.getNombre());

        return sagaRepository.save(saga);
    }

    public void eliminar(Integer id) {
        sagaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Saga no encontrada"));

        sagaRepository.deleteById(id);
    }
}
