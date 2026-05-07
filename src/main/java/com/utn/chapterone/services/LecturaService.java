package com.utn.chapterone.services;

import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.repositories.LecturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturaService {

    @Autowired
    private LecturaRepository lecturaRepository;

    public List<Lectura> obtenerTodos() {
        return lecturaRepository.findAll();
    }

    public Lectura obtenerPorId(Integer id) {
        return lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));
    }

    public Lectura crear(Lectura lectura) {
        return lecturaRepository.save(lectura);
    }

    public Lectura actualizar(Integer id, Lectura lecturaActualizada) {
        Lectura lectura = lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));
        
        lectura.setUsuario(lecturaActualizada.getUsuario());
        lectura.setLibro(lecturaActualizada.getLibro());
        lectura.setEstado(lecturaActualizada.getEstado());
        lectura.setFechaIni(lecturaActualizada.getFechaIni());
        lectura.setFechaFin(lecturaActualizada.getFechaFin());
        
        return lecturaRepository.save(lectura);
    }

    public void eliminar(Integer id) {
        lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));
        
        lecturaRepository.deleteById(id);
    }
}
