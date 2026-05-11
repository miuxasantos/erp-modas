package com.erpmodas.repository;

import com.erpmodas.model.entidades.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    Optional<SessionToken> findByToken(String token);
    List<SessionToken> findByUsuarioId(Long usuarioId);
    List<SessionToken> findByAtivoTrue();
    List<SessionToken> findByUsuarioIdAndAtivoTrue(Long usuarioId);

    @Modifying
    @Query("UPDATE SessionToken s SET s.ativo = false WHERE s.usuario.id = :usuarioId AND s.ativo = true")
    int invalidarTodasSessoesDoUsuario(Long usuarioId);

    @Modifying
    @Query("DELETE FROM SessionToken s WHERE s.dataExp < :dataLimite")
    int deletarTokensExpirados(LocalDateTime dataLimite);
}
