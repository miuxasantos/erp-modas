package com.erpmodas.dto.caixa;

import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.enums.StatusCaixa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaixaResponseDTO {

    private Long id;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private BigDecimal saldoAbertura;
    private BigDecimal saldoFechamento;
    private StatusCaixa statusCaixa;
    private List<MovimentacoesCaixaDTO> movimentacoes;

    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
    private BigDecimal saldoTotal;
}
