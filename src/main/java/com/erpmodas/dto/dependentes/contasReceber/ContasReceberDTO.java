package com.erpmodas.dto.dependentes.contasReceber;

import com.erpmodas.enums.StatusConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasReceberDTO {
    private Long id;
    private String clienteNome;
    private LocalDate dataLancamento;
    private LocalDate dataVencimento;
    private LocalDate dataRecebimento;
    private BigDecimal valor;
    private Integer numeroParcela;
    private Integer totalParcelas;
    private String observacoes;
    private StatusConta statusConta;
}