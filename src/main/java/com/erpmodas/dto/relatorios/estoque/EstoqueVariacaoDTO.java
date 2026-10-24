package com.erpmodas.dto.relatorios.estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueVariacaoDTO {
    private Long id;
    private String sku;
    private String cor;
    private String tamanho;
    private int quantidade;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private BigDecimal valorEstoqueCusto;
    private BigDecimal valorEstoqueVenda;
}
