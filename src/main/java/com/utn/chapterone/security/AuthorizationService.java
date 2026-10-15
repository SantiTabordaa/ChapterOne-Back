package com.utn.chapterone.security;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("authz")
public class AuthorizationService {

    private final UsuarioRepository usuarioRepository;
    private final LecturaRepository lecturaRepository;
    private final ResenaRepository resenaRepository;
    private final ClubRepository clubRepository;
    private final PosteoClubRepository posteoClubRepository;
    private final SolicitudRepository solicitudRepository;
    private final HistorialClubRepository historialClubRepository;

    public AuthorizationService(
            UsuarioRepository usuarioRepository,
            LecturaRepository lecturaRepository,
            ResenaRepository resenaRepository,
            ClubRepository clubRepository,
            PosteoClubRepository posteoClubRepository,
            SolicitudRepository solicitudRepository,
            HistorialClubRepository historialClubRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.lecturaRepository = lecturaRepository;
        this.resenaRepository = resenaRepository;
        this.clubRepository = clubRepository;
        this.posteoClubRepository = posteoClubRepository;
        this.solicitudRepository = solicitudRepository;
        this.historialClubRepository = historialClubRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Integer currentUserId() {
        Authentication auth = getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserPrincipal up) {
            return up.getId();
        }

        String username = auth.getName();
        if (username != null && !username.isBlank()) {
            return usuarioRepository.findByUsername(username)
                    .map(Usuario::getIdUsuario)
                    .orElse(null);
        }

        return null;
    }

    public String currentUsername() {
        Authentication auth = getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : null;
    }

    public boolean isAdmin() {
        Authentication auth = getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> Roles.ROLE_ADMIN.equals(a.getAuthority()));
    }

    public boolean isSelf(Integer usuarioId) {
        if (usuarioId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        return uid != null && uid.equals(usuarioId);
    }

    public boolean ownsLectura(Integer lecturaId) {
        if (lecturaId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return lecturaRepository.findById(lecturaId)
                .map(l -> l.getUsuario() != null && Objects.equals(l.getUsuario().getIdUsuario(), uid))
                .orElse(false);
    }

    public boolean ownsResena(Integer resenaId) {
        if (resenaId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return resenaRepository.findById(resenaId)
                .map(r -> r.getLectura() != null
                        && r.getLectura().getUsuario() != null
                        && Objects.equals(r.getLectura().getUsuario().getIdUsuario(), uid))
                .orElse(false);
    }

    public boolean isClubAdmin(Integer clubId) {
        if (clubId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return clubRepository.findById(clubId)
                .map(c -> c.getAdmin() != null && Objects.equals(c.getAdmin().getIdUsuario(), uid))
                .orElse(false);
    }

    public boolean canEditPosteo(Integer posteoId) {
        if (posteoId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return posteoClubRepository.findById(posteoId)
                .map(p -> p.getUsuario() != null && Objects.equals(p.getUsuario().getIdUsuario(), uid))
                .orElse(false);
    }

    public boolean canDeletePosteo(Integer posteoId) {
        if (posteoId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return posteoClubRepository.findById(posteoId)
                .map(p -> (p.getUsuario() != null && Objects.equals(p.getUsuario().getIdUsuario(), uid))
                        || (p.getClub() != null && p.getClub().getAdmin() != null && Objects.equals(p.getClub().getAdmin().getIdUsuario(), uid)))
                .orElse(false);
    }

    public boolean canManageSolicitud(Integer solicitudId) {
        if (solicitudId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return solicitudRepository.findById(solicitudId)
                .map(s -> s.getClub() != null && s.getClub().getAdmin() != null && Objects.equals(s.getClub().getAdmin().getIdUsuario(), uid))
                .orElse(false);
    }

    public boolean canDeleteSolicitud(Integer solicitudId) {
        if (solicitudId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return solicitudRepository.findById(solicitudId)
                .map(s -> (s.getUsuario() != null && Objects.equals(s.getUsuario().getIdUsuario(), uid))
                        || (s.getClub() != null && s.getClub().getAdmin() != null && Objects.equals(s.getClub().getAdmin().getIdUsuario(), uid)))
                .orElse(false);
    }

    public boolean canManageHistorial(Integer historialId) {
        if (historialId == null) return false;
        if (isAdmin()) return true;
        Integer uid = currentUserId();
        if (uid == null) return false;

        return historialClubRepository.findById(historialId)
                .map(h -> h.getClub() != null && h.getClub().getAdmin() != null && Objects.equals(h.getClub().getAdmin().getIdUsuario(), uid))
                .orElse(false);
    }
}
