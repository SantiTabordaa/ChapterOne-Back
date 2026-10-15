package com.utn.chapterone.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;

public class UserPrincipal implements UserDetails, Principal {
    private final Integer id;
    private final String username;
    private final String rol;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String username, String rol, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
