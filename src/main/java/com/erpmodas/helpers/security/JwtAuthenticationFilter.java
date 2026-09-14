package com.erpmodas.helpers.security;

import com.erpmodas.model.entidades.Usuario;
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

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if(!jwtService.tokenValido(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtService.extrairEmail(token);
            Usuario usuario = usuarioRepository.findUsuarioByEmail(email).orElse(null);

            boolean sessaoAtiva = sessionTokenRepository.findByToken(token)
                    .map(s -> s.estaValido())
                    .orElse(false);

            if (!sessaoAtiva) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if(usuario != null) {
                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + usuario.getCargo().name())
                );

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        usuario, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("USUÁRIO: " + usuario.getEmail());
                System.out.println("CARGO: " + usuario.getCargo());
                System.out.println(
                        "AUTENTICADO: " +
                                SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .isAuthenticated()
                );
            }
        } catch (JwtException | IllegalArgumentException exception) {
            log.error("Erro ao autenticar token JWT", exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
