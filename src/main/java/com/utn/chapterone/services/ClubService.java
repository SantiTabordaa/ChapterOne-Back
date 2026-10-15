package com.utn.chapterone.services;

import com.utn.chapterone.dto.club.ClubListadoDTO;
import com.utn.chapterone.entities.Club;
import com.utn.chapterone.repositories.ClubRepository;
import com.utn.chapterone.repositories.SolicitudRepository;
import com.utn.chapterone.repositories.UsuarioRepository;
import com.utn.chapterone.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authz;

    public ClubService(
            ClubRepository clubRepository,
            SolicitudRepository solicitudRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authz
    ) {
        this.clubRepository = clubRepository;
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.authz = authz;
    }

    public List<Club> obtenerTodos() {
        return clubRepository.findAll();
    }

    public Club obtenerPorId(Integer id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club no encontrado"));
    }

    public Club crear(Club club) {
        Integer uid = authz.currentUserId();
        if (uid != null) {
            usuarioRepository.findById(uid).ifPresent(club::setAdmin);
        }
        return clubRepository.save(club);
    }

    public Club actualizar(Integer id, Club clubActualizado) {
        Club club = obtenerPorId(id);

        club.setNombreClub(clubActualizado.getNombreClub());
        club.setDescrip(clubActualizado.getDescrip());
        club.setGenero(clubActualizado.getGenero());
        club.setPrivado(clubActualizado.isPrivado());

        if (authz.isAdmin() && clubActualizado.getAdmin() != null) {
            club.setAdmin(clubActualizado.getAdmin());
        }

        return clubRepository.save(club);
    }

    public void eliminar(Integer id) {
        Club club = obtenerPorId(id);
        clubRepository.delete(club);
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