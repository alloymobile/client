package com.alloymobiletech.client.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenProvider tokenProvider;

    public AuthenticationManager(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;
        try {
            username = tokenProvider.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && tokenProvider.validateToken(authToken)) {
            Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.entrySet().stream().filter(e->e.getValue().toString().equalsIgnoreCase("role"))
                    .map(Map.Entry::getKey).collect(Collectors.toList());

            List authorities = rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}