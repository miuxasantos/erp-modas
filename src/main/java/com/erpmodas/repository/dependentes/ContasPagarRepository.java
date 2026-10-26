package com.erpmodas.repository.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Fornecedor;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> {

    List<ContasPagar> findByDataPagamento(LocalDate dataPagamento);
    List<ContasPagar> findByDataPagamentoIsNull();
    List<ContasPagar> findByDataLancamento(LocalDate dataLancamento);
    List<ContasPagar> findByDataVencimento(LocalDate dataVencimento);
    List<ContasPagar> findByDataVencimentoBefore(LocalDate hoje);
    List<ContasPagar> findByDataPagamentoBetween(LocalDate dataLancamento, LocalDate dataVencimento);
    List<ContasPagar> findByDataVencimentoBetween(LocalDate dataLancamento, LocalDate dataVencimento);
    List<ContasPagar> findByDataVencimentoBeforeAndDataPagamentoIsNull(LocalDate hoje);
    List<ContasPagar> findByStatusConta(StatusConta statusConta);
    List<ContasPagar> findByCompraId(Long compraId);

    @Query("""
        SELECT c FROM ContasPagar c
        LEFT JOIN FETCH c.compra c2
        WHERE COALESCE(c2.dataChegada, c.dataLancamento) BETWEEN :inicio AND :fim
        """)
    List<ContasPagar> buscarPorDataOrigem(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
