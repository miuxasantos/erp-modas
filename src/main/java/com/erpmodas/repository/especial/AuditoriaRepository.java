package com.erpmodas.repository.especial;

import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.model.entidades.especial.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByUsuarioId(Long usuarioId);
    List<Auditoria> findByEntidade(String entidade);
    List<Auditoria> findByTipoAcaoAud(TipoAcaoAud tipoAcaoAud);
    List<Auditoria> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
