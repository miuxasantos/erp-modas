package com.erpmodas.dto.dependentes.contasPagar;

import com.erpmodas.dto.compra.CompraDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPagarDTO {

    private Long id;
    private LocalDate dataLancamento;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private CompraDTO compra;
    private BigDecimal valor;
    private Integer numeroParcela;
    private Integer totalParcelas;
    private String observacoes;
    private StatusConta statusConta;
}
