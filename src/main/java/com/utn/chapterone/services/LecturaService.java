package com.utn.chapterone.services;

import com.utn.chapterone.entities.Lectura;
import com.utn.chapterone.repositories.LecturaRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturaService {

    private final LecturaRepository lecturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authz;

    public LecturaService(
            LecturaRepository lecturaRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authz
    ) {
        this.lecturaRepository = lecturaRepository;
        this.usuarioRepository = usuarioRepository;
        this.authz = authz;
    }

    public List<Lectura> obtenerTodos() {
        return lecturaRepository.findAll();
    }

    public Lectura obtenerPorId(Integer id) {
        return lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));
    }

    public Lectura crear(Lectura lectura) {
        Integer uid = authz.currentUserId();
        if (uid != null) {
            usuarioRepository.findById(uid).ifPresent(lectura::setUsuario);
        }
        return lecturaRepository.save(lectura);
    }

    public Lectura actualizar(Integer id, Lectura lecturaActualizada) {
        Lectura lectura = lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));

        lectura.setLibro(lecturaActualizada.getLibro());
        lectura.setEstado(lecturaActualizada.getEstado());
        lectura.setFechaIni(lecturaActualizada.getFechaIni());
        lectura.setFechaFin(lecturaActualizada.getFechaFin());
        if (authz.isAdmin() && lecturaActualizada.getUsuario() != null) {
            lectura.setUsuario(lecturaActualizada.getUsuario());
        }

        return lecturaRepository.save(lectura);
    }

    public void eliminar(Integer id) {
        Lectura lectura = lecturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura no encontrada"));

        lecturaRepository.deleteById(id);
    }
}
