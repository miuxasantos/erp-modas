package com.erpmodas.dto.relatorios.financeiro;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DiaFiscalDTO {
    private LocalDate data;

    private BigDecimal entradaDinheiro = BigDecimal.ZERO;
    private BigDecimal entradaPix = BigDecimal.ZERO;
    private BigDecimal entradaCartao = BigDecimal.ZERO;
    private BigDecimal entradaOutros = BigDecimal.ZERO;
    private BigDecimal totalEntradas = BigDecimal.ZERO;

    private BigDecimal saidaDinheiro = BigDecimal.ZERO;
    private BigDecimal saidaPix = BigDecimal.ZERO;
    private BigDecimal saidaCartao = BigDecimal.ZERO;
    private BigDecimal saidaOutros = BigDecimal.ZERO;
    private BigDecimal totalSaidas = BigDecimal.ZERO;

    private BigDecimal saldo = BigDecimal.ZERO;
}