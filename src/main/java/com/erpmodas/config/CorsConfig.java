package com.erpmodas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Origens permitidas — onde seu front roda
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",   // Vite (React/Vue)
                "http://localhost:3000",   // Create React App / Next.js
                "http://localhost:4200"    // Angular
        ));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers que o front pode enviar
        config.setAllowedHeaders(List.of("*"));

        // Headers que o back devolve e o front pode ler
        config.setExposedHeaders(List.of("Authorization", "Location"));

        // Permite envio de cookies/credentials (importante se você usa autenticação)
        config.setAllowCredentials(true);

        // Tempo (em segundos) que o navegador pode cachear a resposta de preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // aplica a todos os endpoints
        return source;
    }
}
