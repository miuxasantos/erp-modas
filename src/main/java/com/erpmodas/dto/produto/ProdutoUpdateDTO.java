package com.erpmodas.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoUpdateDTO {
    private String nome;
    private String descricao;
    private Boolean ativo;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String tecido;
    private String marca;
    private Long categoriaId;
    private String imagem;
}
