package com.erpmodas.dto.relatorios.compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResumoDTO {
    private Long id;
    private String lote;
    private LocalDate dataChegada;
    private String fornecedor;
    private int unidadesAdicionadas;
    private int produtosDistintos;
    private BigDecimal valorTotal;
}
