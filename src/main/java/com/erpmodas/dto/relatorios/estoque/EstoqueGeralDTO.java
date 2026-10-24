package com.erpmodas.dto.relatorios.estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueGeralDTO {
    private int quantidadeProdutos;
    private int produtosComEstoque;
    private int quantidadeVariacoes;
    private int variacoesSemEstoque;
    private int quantidadeUnidades;
    private BigDecimal valorEstoqueCusto;
    private BigDecimal valorEstoqueVenda;
}
