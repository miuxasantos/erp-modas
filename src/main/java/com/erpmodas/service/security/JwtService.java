package com.erpmodas.service.security;

import com.erpmodas.model.entidades.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-hours")
    private long expirationHours;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        long expirationMillis = expirationHours * 60 * 60 * 1000;
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("usuarioId", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("cargo", usuario.getCargo())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();

    }

    public Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairRole(String token) {
        return extrairClaims(token).get("cargo", String.class);
    }

    public Long obterUsuarioId(String token) {
        Claims claims = extrairClaims(token);
        return claims.get("id", Long.class);
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = extrairClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
