package com.erpmodas.repository;

import com.erpmodas.model.entidades.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    Optional<SessionToken> findByToken(String token);
    List<SessionToken> findByUsuarioId(Long usuarioId);
    List<SessionToken> findByAtivoTrue();
}
