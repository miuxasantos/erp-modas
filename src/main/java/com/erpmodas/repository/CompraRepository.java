package com.erpmodas.repository;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.model.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByFornecedorId(Long fornecedorId);
    List<Compra> findByDataChegadaBetween(LocalDate inicio, LocalDate fim);
    List<Compra> findByFormaPagamento(FormaPagamento formaPagamento);
    List<Compra> findTop10ByOrderByDataChegadaDesc();

    @Query("""
    SELECT SUM(i.subTotal)
    FROM Compra c
    JOIN c.itensCompra i
    WHERE c.dataChegada BETWEEN :inicio AND :fim
    """)
    BigDecimal gastosPeriodo(LocalDate inicio, LocalDate fim);
}
