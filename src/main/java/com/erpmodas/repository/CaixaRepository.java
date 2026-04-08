package com.erpmodas.repository;

import com.erpmodas.model.entidades.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
    Optional<Caixa> findByDataAbertura(LocalDate dataAbertura);
    Optional<Caixa> findByDataFechamento(LocalDate dataFechamento);
    Optional<Caixa> findTop1ByOrderByDataAberturaDesc();
    List<Caixa> findAllByOrderByDataFechamentoDesc();
    List<Caixa> findByDataAberturaBetween(LocalDate inicio, LocalDate fim);
    List<Caixa> findBySaldoAberturaGreaterThan(BigDecimal valor);
    List<Caixa> findBySaldoFechamentoGreaterThan(BigDecimal valor);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.caixa.id = :caixaId
    AND m.tipoMovCaixa = 'ENTRADA'
    """)
    BigDecimal totalEntradasPorCaixa(Long caixaId);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.caixa.id = :caixaId
    AND m.tipoMovCaixa = 'SAIDA'
    """)
    BigDecimal totalSaidasPorCaixa(Long caixaId);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.data BETWEEN :inicio AND :fim
    AND m.tipoMovCaixa = 'ENTRADA'
    """)
    BigDecimal entradasPeriodo(LocalDate inicio, LocalDate fim);

    @Query("""
    SELECT SUM(m.valor)
    FROM MovimentacoesCaixa m
    WHERE m.data BETWEEN :inicio AND :fim
    AND m.tipoMovCaixa = 'SAIDA'
    """)
    BigDecimal saidasPeriodo(LocalDate inicio, LocalDate fim);
}
