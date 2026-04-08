package com.erpmodas.repository;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.model.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByClienteId(Long clienteId);
    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);
    List<Venda> findByFormaPagamento(FormaPagamento formaPagamento);
    List<Venda> findTop10ByOrderByDataVendaDesc();

    @Query("""
    SELECT SUM(i.subTotal)
    FROM Venda v
    JOIN v.itensVenda i
    WHERE v.dataVenda BETWEEN :inicio AND :fim
    """)
    BigDecimal faturamentoPeriodo(LocalDate inicio, LocalDate fim);
}
