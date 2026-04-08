package com.erpmodas.repository.dependentes;

import com.erpmodas.enums.TipoMovCaixa;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MovimentacoesCaixaRepository extends JpaRepository<MovimentacoesCaixa, Long> {
    List<MovimentacoesCaixa> findByCaixaId(Long caixaId);
    List<MovimentacoesCaixa> findByTipoMovCaixa(TipoMovCaixa tipoMovCaixa);
    List<MovimentacoesCaixa> findByDataBetween(LocalDate inicio, LocalDate fim);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.tipoMovCaixa = 'ENTRADA' AND m.data BETWEEN :inicio AND :fim
    """)
    BigDecimal totalEntradas(LocalDate inicio, LocalDate fim);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.tipoMovCaixa = 'SAIDA' AND m.data BETWEEN :inicio AND :fim
    """)
    BigDecimal totalSaidas(LocalDate inicio, LocalDate fim);
}
