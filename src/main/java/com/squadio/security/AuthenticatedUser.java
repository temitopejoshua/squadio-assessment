package com.squadio.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AuthenticatedUser implements UserDetails {

    private Long id;
    private String userId;
    private String username;
    private String name;
    private String password;
    private boolean active = true;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private String accessPlatform;
    private String clientType;
    private boolean isAdmin;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void addAuthority(String name) {
        authorities.add(new SimpleGrantedAuthority(name));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAdmin() {
        return authorities.stream().anyMatch((sg) -> sg.getAuthority().equalsIgnoreCase("ADMIN"));
    }
}
