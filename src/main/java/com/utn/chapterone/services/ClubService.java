package com.utn.chapterone.services;

import com.utn.chapterone.dto.club.ClubListadoDTO;
import com.utn.chapterone.entities.Club;
import com.utn.chapterone.repositories.ClubRepository;
import com.utn.chapterone.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    public List<Club> obtenerTodos() {
        return clubRepository.findAll();
    }

    public Club obtenerPorId(Integer id) {
        return clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));
    }

    public Club crear(Club club) {
        return clubRepository.save(club);
    }

    public Club actualizar(Integer id, Club clubActualizado) {
        Club club = clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        
        club.setNombreClub(clubActualizado.getNombreClub());
        club.setDescrip(clubActualizado.getDescrip());
        club.setGenero(clubActualizado.getGenero());
        club.setAdmin(clubActualizado.getAdmin());
        club.setPrivado(clubActualizado.isPrivado());
        
        return clubRepository.save(club);
    }

    public void eliminar(Integer id) {
        clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        
        clubRepository.deleteById(id);
    }

    public List<ClubListadoDTO> obtenerTodosListado() {
        return clubRepository.findAll().stream()
            .map(this::convertirAListadoDTO)
            .collect(Collectors.toList());
    }

    private ClubListadoDTO convertirAListadoDTO(Club club) {
        Long cantidadIntegrantes = solicitudRepository.contarIntegrantesAceptadosPorClub(club.getIdClub());
        
        return new ClubListadoDTO(
            club.getIdClub(),
            club.getNombreClub(),
            club.getDescrip(),
            club.getGenero() != null ? club.getGenero().getNombreGen() : null,
            cantidadIntegrantes,
            club.isPrivado()
        );
    }
}