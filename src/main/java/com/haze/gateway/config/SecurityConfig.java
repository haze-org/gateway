package com.haze.gateway.config;

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
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                // 1. Handle Browser Logins (Cookies)
                .oauth2Login(Customizer.withDefaults())

                // 2. Handle Postman/Machine calls (JWTs)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        // Disable CSRF for now to make Postman testing easier (will enable it once FE is ready)
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }
}