package com.erpmodas.dto.relatorios.financeiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContasPeriodoDTO {
    private LocalDate inicio;
    private LocalDate fim;
    private int quantidadeContas;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private BigDecimal valorEmAberto;
}