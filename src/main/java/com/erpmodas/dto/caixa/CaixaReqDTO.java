package com.erpmodas.dto.caixa;

import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.enums.StatusCaixa;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaixaReqDTO {
    @NotNull
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    @NotNull
    private BigDecimal saldoAbertura;
    private BigDecimal saldoFechamento;
    private StatusCaixa statusCaixa;
    @NotEmpty
    @NotNull
    private List<MovimentacoesCaixaDTO> movimentacoes;
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
    private BigDecimal saldoTotal;
}
