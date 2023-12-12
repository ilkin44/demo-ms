package com.example.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
public class MyUserPrincipal extends User {
    public MyUserPrincipal( String email, boolean enabled, boolean accountNonExpired,
                           boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(email, "password", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    }
}