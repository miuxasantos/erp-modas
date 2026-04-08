package com.erpmodas.repository.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Fornecedor;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> {

    List<ContasPagar> findByDataPagamento(LocalDate dataPagamento);
    List<ContasPagar> findByDataPagamentoIsNull();
    List<ContasPagar> findByDataLancamento(LocalDate dataLancamento);
    List<ContasPagar> findByDataVencimento(LocalDate dataVencimento);
    List<ContasPagar> findByDataVencimentoBefore(LocalDate hoje);
    List<ContasPagar> findByDataPagamentoBetween(LocalDate dataLancamento, LocalDate dataVencimento);
    List<ContasPagar> findByDataVencimentoBeforeAndDataPagamentoIsNull(LocalDate hoje);
    List<ContasPagar> findByStatusConta(StatusConta statusConta);
    List<ContasPagar> findByCompraId(Long compraId);
    List<ContasPagar> findByFornecedorId(Long fornecedorId);
    List<ContasPagar> findByFormaPagamento(FormaPagamento formaPagamento);
    List<ContasPagar> findByFornecedorIdAndDataLancamento(Long id_fornecedor, LocalDate dataLancamento);

}
