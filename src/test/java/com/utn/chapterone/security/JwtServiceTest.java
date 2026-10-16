package com.utn.chapterone.security;

import com.utn.chapterone.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "12345678901234567890123456789012"; // 32 bytes

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secret, 3600000);
    }

    @Test
    void testSecretTooShortThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new JwtService("short-secret", 3600000));
    }

    @Test
    void testGenerateTokenAndExtractClaimsForRegularUser() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(42);
        usuario.setUsername("juan.lector");
        usuario.setRol(Roles.USER);
        usuario.setUrlFotoPerfil("https://example.com/avatar.png");

        String token = jwtService.generateToken(usuario);
        assertNotNull(token);

        assertEquals("juan.lector", jwtService.extractUsername(token));
        assertEquals(42, jwtService.extractUserId(token));
        assertEquals(Roles.USER, jwtService.extractRol(token));
        assertFalse(jwtService.isTokenExpired(token));
        assertTrue(jwtService.isTokenValid(token, "juan.lector"));
        assertFalse(jwtService.isTokenValid(token, "otro.usuario"));

        String fotoClaim = jwtService.extractClaim(token, c -> c.get("urlFotoPerfil", String.class));
        assertEquals("https://example.com/avatar.png", fotoClaim);
    }

    @Test
    void testGenerateTokenAndExtractClaimsForAdmin() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setUsername("ana.admin");
        usuario.setRol(Roles.ADMIN);

        String token = jwtService.generateToken(usuario);
        assertNotNull(token);

        assertEquals("ana.admin", jwtService.extractUsername(token));
        assertEquals(1, jwtService.extractUserId(token));
        assertEquals(Roles.ADMIN, jwtService.extractRol(token));
    }

    @Test
    void testGenerateTokenForModerator() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10);
        usuario.setUsername("carlos.mod");
        usuario.setRol(Roles.MODERATOR);

        String token = jwtService.generateToken(usuario);
        assertEquals(Roles.MODERATOR, jwtService.extractRol(token));
    }
}
