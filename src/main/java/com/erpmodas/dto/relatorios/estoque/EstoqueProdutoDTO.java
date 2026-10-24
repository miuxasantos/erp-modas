package com.erpmodas.dto.relatorios.estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueProdutoDTO {
    private Long id;
    private Integer codigo;
    private String nome;
    private String categoria;
    private boolean ativo;
    private int quantidadeVariacoes;
    private int quantidadeUnidades;
    private BigDecimal valorEstoqueCusto;
    private BigDecimal valorEstoqueVenda;
    private List<EstoqueVariacaoDTO> variacoes;
}
