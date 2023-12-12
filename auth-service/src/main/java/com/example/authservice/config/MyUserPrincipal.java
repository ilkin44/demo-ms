package com.example.authservice.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@Data
public class MyUserPrincipal {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

}
