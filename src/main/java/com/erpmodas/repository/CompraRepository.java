package com.erpmodas.repository;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.model.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByFornecedorId(Long fornecedorId);
    List<Compra> findByDataChegadaBetween(LocalDate inicio, LocalDate fim);
    List<Compra> findByFormaPagamento(FormaPagamento formaPagamento);
    List<Compra> findTop10ByOrderByDataChegadaDesc();
    Optional<Compra> findCompraById(Long id);

    @Query("SELECT c FROM Compra c LEFT JOIN FETCH c.itensCompra WHERE c.id = :id")
    Optional<Compra> findByIdWithItens(@Param("id") Long id);

    @Query("""
    SELECT SUM(i.subTotal)
    FROM Compra c
    JOIN c.itensCompra i
    WHERE c.dataChegada BETWEEN :inicio AND :fim
    """)
    BigDecimal gastosPeriodo(LocalDate inicio, LocalDate fim);
}
