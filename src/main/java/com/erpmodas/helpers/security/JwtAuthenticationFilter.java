package com.erpmodas.helpers.security;

import com.erpmodas.repository.SessionTokenRepository;
import com.erpmodas.repository.UsuarioRepository;
import com.erpmodas.service.security.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final SessionTokenRepository sessionTokenRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        SecurityContextHolder.clearContext();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            autenticar(authHeader.substring(7));
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            log.debug("Token JWT rejeitado: {}", e.getClass().getSimpleName());
        }

        filterChain.doFilter(request, response);
    }

    private void autenticar(String token) {
        if (!jwtService.tokenValido(token)) {
            return;
        }

        boolean sessaoAtiva = sessionTokenRepository.findByToken(token)
                .map(sessao -> sessao.estaValido())
                .orElse(false);

        if (!sessaoAtiva) {
            return;
        }

        String email = jwtService.extrairEmail(token);

        usuarioRepository.findUsuarioByEmail(email).ifPresent(usuario -> {
            var authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + usuario.getCargo().name())
            );

            var authentication = new UsernamePasswordAuthenticationToken(
                    usuario, null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }
}
