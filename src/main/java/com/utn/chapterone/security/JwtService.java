package com.utn.chapterone.security;

import com.utn.chapterone.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:3600000}") long expirationMs
    ) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT_SECRET debe tener al menos 32 caracteres.");
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", usuario.getIdUsuario());
        claims.put("rol", usuario.getRol());
        claims.put("urlFotoPerfil", usuario.getUrlFotoPerfil());

        return generateToken(usuario.getUsername(), claims);
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Object uid = claims.get("uid");
        if (uid instanceof Integer) {
            return (Integer) uid;
        } else if (uid instanceof Number) {
            return ((Number) uid).intValue();
        }
        return null;
    }

    public String extractRol(String token) {
        Claims claims = extractAllClaims(token);
        String rol = claims.get("rol", String.class);
        if (rol != null && !rol.isBlank()) {
            return rol;
        }
        return Roles.USER;
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String subject = extractUsername(token);
            return subject != null && subject.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}
