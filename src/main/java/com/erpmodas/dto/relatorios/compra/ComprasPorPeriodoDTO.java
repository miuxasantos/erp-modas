package com.erpmodas.dto.relatorios.compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComprasPorPeriodoDTO {
    private LocalDate inicio;
    private LocalDate fim;
    private int quantidadeCompras;
    private BigDecimal valorTotal;
    private BigDecimal ticketMedio;
    private List<CompraResumoDTO> compras;
}
