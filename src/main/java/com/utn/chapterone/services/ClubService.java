package com.utn.chapterone.services;

import com.utn.chapterone.entities.Club;
import com.utn.chapterone.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

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
}
