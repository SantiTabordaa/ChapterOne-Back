package com.utn.chapterone.security;

public final class Roles {
    private Roles() {}

    // Valores en base de datos y token
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String MODERATOR = "moderator";

    // Autoridades de Spring Security (prefijo ROLE_)
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";

    public static String toAuthority(String rol) {
        if (rol == null || rol.isBlank()) {
            return ROLE_USER;
        }
        return "ROLE_" + rol.trim().toUpperCase();
    }

    public static boolean esRolValido(String rol) {
        if (rol == null) return false;
        String r = rol.trim().toLowerCase();
        return r.equals(USER) || r.equals(ADMIN) || r.equals(MODERATOR);
    }
}
