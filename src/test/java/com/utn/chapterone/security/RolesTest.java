package com.utn.chapterone.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolesTest {

    @Test
    void testToAuthority() {
        assertEquals("ROLE_USER", Roles.toAuthority("user"));
        assertEquals("ROLE_ADMIN", Roles.toAuthority("admin"));
        assertEquals("ROLE_MODERATOR", Roles.toAuthority("moderator"));
        assertEquals("ROLE_USER", Roles.toAuthority(null));
        assertEquals("ROLE_USER", Roles.toAuthority(""));
        assertEquals("ROLE_CUSTOM", Roles.toAuthority("custom"));
    }

    @Test
    void testEsRolValido() {
        assertTrue(Roles.esRolValido("user"));
        assertTrue(Roles.esRolValido("admin"));
        assertTrue(Roles.esRolValido("moderator"));
        assertTrue(Roles.esRolValido("USER"));
        assertTrue(Roles.esRolValido("ADMIN"));
        assertFalse(Roles.esRolValido("invitado"));
        assertFalse(Roles.esRolValido("superadmin"));
        assertFalse(Roles.esRolValido(null));
        assertFalse(Roles.esRolValido(""));
    }
}
