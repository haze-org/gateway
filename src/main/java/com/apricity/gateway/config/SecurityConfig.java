package com.apricity.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                // all requests are protected
                .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                // for humans login, redirects to login page
                .oauth2Login(Customizer.withDefaults())
                // for machines login, when a client sends jwt token, we verify it against issuer uri from keycloak
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                // csrf disabled for development
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }
}