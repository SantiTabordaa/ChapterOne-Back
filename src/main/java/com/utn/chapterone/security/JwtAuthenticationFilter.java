package com.utn.chapterone.security;

import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception ex) {
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(token, username)) {
                Integer userId = jwtService.extractUserId(token);
                String rol = jwtService.extractRol(token);

                // Si el token es de una versión anterior y no tenía uid o rol, lo resolvemos desde la base
                if (userId == null || rol == null) {
                    Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
                    if (usuarioOpt.isPresent()) {
                        Usuario u = usuarioOpt.get();
                        userId = u.getIdUsuario();
                        rol = u.getRol();
                    } else {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }

                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(Roles.toAuthority(rol))
                );

                UserPrincipal principal = new UserPrincipal(userId, username, rol, authorities);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(principal, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
