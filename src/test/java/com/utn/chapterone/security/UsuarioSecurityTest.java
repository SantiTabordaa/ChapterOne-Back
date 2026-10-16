package com.utn.chapterone.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.chapterone.dto.usuario.UsuarioPublicoDTO;
import com.utn.chapterone.entities.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioSecurityTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testPasswordNeverSerializedInJson() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(5);
        usuario.setNombre("Carlos");
        usuario.setApellido("Perez");
        usuario.setEmail("carlos@example.com");
        usuario.setUsername("carlos");
        usuario.setPassword("SuperPassword123");
        usuario.setRol(Roles.USER);

        String json = objectMapper.writeValueAsString(usuario);

        assertFalse(json.contains("SuperPassword123"), "El JSON no debe contener la contraseña en texto claro ni hasheada");
        assertFalse(json.contains("\"password\""), "El JSON de Usuario no debe serializar el campo password");
        assertTrue(json.contains("\"username\":\"carlos\""));
        assertTrue(json.contains("\"rol\":\"user\""));
    }

    @Test
    void testRoleHelpersAndDefaults() {
        Usuario usuario = new Usuario();
        assertEquals(Roles.USER, usuario.getRol());
        assertFalse(usuario.isAdmin());
        assertFalse(usuario.isModerator());

        usuario.setRol(Roles.ADMIN);
        assertEquals(Roles.ADMIN, usuario.getRol());
        assertTrue(usuario.isAdmin());

        usuario.setRol(Roles.USER);
        assertEquals(Roles.USER, usuario.getRol());
        assertFalse(usuario.isAdmin());

        usuario.setRol(Roles.MODERATOR);
        assertEquals(Roles.MODERATOR, usuario.getRol());
        assertTrue(usuario.isModerator());
        assertFalse(usuario.isAdmin());
    }

    @Test
    void testUsuarioPublicoDTODoesNotExposePassword() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(12);
        usuario.setNombre("Lucia");
        usuario.setApellido("Gomez");
        usuario.setEmail("lucia@example.com");
        usuario.setUsername("lucia.g");
        usuario.setPassword("MiClaveSecreta123");
        usuario.setUrlFotoPerfil("https://avatar.url/lucia.jpg");
        usuario.setRol(Roles.USER);

        UsuarioPublicoDTO dto = new UsuarioPublicoDTO(usuario);

        assertEquals(12, dto.getIdUsuario());
        assertEquals("Lucia", dto.getNombre());
        assertEquals("Gomez", dto.getApellido());
        assertEquals("lucia@example.com", dto.getEmail());
        assertEquals("lucia.g", dto.getUsername());
        assertEquals("https://avatar.url/lucia.jpg", dto.getUrlFotoPerfil());
        assertEquals(Roles.USER, dto.getRol());

        String json = objectMapper.writeValueAsString(dto);
        assertFalse(json.contains("password"));
        assertFalse(json.contains("MiClaveSecreta123"));
    }
}
