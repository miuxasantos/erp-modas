package com.erpmodas.repository.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Cliente;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContasReceberRepository extends JpaRepository<ContasReceber, Long> {

    List<ContasReceber> findByDataLancamento(LocalDate dataLancamento);
    List<ContasReceber> findByDataVencimento(LocalDate dataVencimento);
    List<ContasReceber> findByDataRecebimento(LocalDate dataPagamento);
    List<ContasReceber> findByDataRecebimentoBetween(LocalDate inicio, LocalDate fim);
    List<ContasReceber> findByDataRecebimentoIsNull();
    List<ContasReceber> findByDataVencimentoBefore(LocalDate hoje);
    List<ContasReceber> findByDataVencimentoBeforeAndDataRecebimentoIsNull(LocalDate hoje);
    List<ContasReceber> findByVendaId(Long vendaId);
    List<ContasReceber> findByClienteId(Long clienteId);
    List<ContasReceber> findByFormaPagamento(FormaPagamento formaPagamento);
    List<ContasReceber> findByStatusConta(StatusConta statusConta);
    List<ContasReceber> findByClienteIdAndDataLancamento(Long id_cliente, LocalDate dataLancamento);
}
