package com.utn.chapterone.security;

import com.utn.chapterone.entities.*;
import com.utn.chapterone.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private LecturaRepository lecturaRepository;
    @Mock private ResenaRepository resenaRepository;
    @Mock private ClubRepository clubRepository;
    @Mock private PosteoClubRepository posteoClubRepository;
    @Mock private SolicitudRepository solicitudRepository;
    @Mock private HistorialClubRepository historialClubRepository;

    @InjectMocks
    private AuthorizationService authz;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateUser(Integer id, String username, String rol) {
        var authority = new SimpleGrantedAuthority(Roles.toAuthority(rol));
        UserPrincipal principal = new UserPrincipal(id, username, rol, List.of(authority));
        var auth = new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testIsSelf() {
        authenticateUser(10, "juan", Roles.USER);

        assertTrue(authz.isSelf(10));
        assertFalse(authz.isSelf(20));
        assertFalse(authz.isSelf(null));
        assertFalse(authz.isAdmin());
    }

    @Test
    void testAdminBypassesIsSelf() {
        authenticateUser(1, "ana.admin", Roles.ADMIN);

        assertTrue(authz.isAdmin());
        assertTrue(authz.isSelf(1));
        assertTrue(authz.isSelf(999));
    }

    @Test
    void testOwnsLectura() {
        authenticateUser(5, "lector", Roles.USER);

        Usuario user5 = new Usuario();
        user5.setIdUsuario(5);
        Lectura lecturaPropia = new Lectura();
        lecturaPropia.setIdLectura(101);
        lecturaPropia.setUsuario(user5);

        Usuario user8 = new Usuario();
        user8.setIdUsuario(8);
        Lectura lecturaAjena = new Lectura();
        lecturaAjena.setIdLectura(102);
        lecturaAjena.setUsuario(user8);

        when(lecturaRepository.findById(101)).thenReturn(Optional.of(lecturaPropia));
        when(lecturaRepository.findById(102)).thenReturn(Optional.of(lecturaAjena));
        when(lecturaRepository.findById(999)).thenReturn(Optional.empty());

        assertTrue(authz.ownsLectura(101));
        assertFalse(authz.ownsLectura(102));
        assertFalse(authz.ownsLectura(999));
    }

    @Test
    void testIsClubAdmin() {
        authenticateUser(7, "adminClub", Roles.USER);

        Usuario admin = new Usuario();
        admin.setIdUsuario(7);
        Club clubPropio = new Club();
        clubPropio.setIdClub(1);
        clubPropio.setAdmin(admin);

        Usuario otro = new Usuario();
        otro.setIdUsuario(9);
        Club clubAjeno = new Club();
        clubAjeno.setIdClub(2);
        clubAjeno.setAdmin(otro);

        when(clubRepository.findById(1)).thenReturn(Optional.of(clubPropio));
        when(clubRepository.findById(2)).thenReturn(Optional.of(clubAjeno));

        assertTrue(authz.isClubAdmin(1));
        assertFalse(authz.isClubAdmin(2));
    }

    @Test
    void testCanDeletePosteoByAuthorOrClubAdmin() {
        authenticateUser(3, "autorPosteo", Roles.USER);

        Usuario autor = new Usuario();
        autor.setIdUsuario(3);

        Usuario adminClub = new Usuario();
        adminClub.setIdUsuario(4);

        Club club = new Club();
        club.setAdmin(adminClub);

        PosteoClub posteo = new PosteoClub();
        posteo.setIdPosteo(50);
        posteo.setUsuario(autor);
        posteo.setClub(club);

        when(posteoClubRepository.findById(50)).thenReturn(Optional.of(posteo));

        // Como autor: puede editar y borrar
        assertTrue(authz.canEditPosteo(50));
        assertTrue(authz.canDeletePosteo(50));

        // Como admin del club (pero no autor): no puede editar el texto pero SI puede borrarlo
        authenticateUser(4, "adminClub", Roles.USER);
        assertFalse(authz.canEditPosteo(50));
        assertTrue(authz.canDeletePosteo(50));

        // Como tercero ajeno: no puede ni editar ni borrar
        authenticateUser(99, "tercero", Roles.USER);
        assertFalse(authz.canEditPosteo(50));
        assertFalse(authz.canDeletePosteo(50));
    }
}
