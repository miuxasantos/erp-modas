package com.erpmodas.dto.apoio.variacaoProdutoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProdutoUpdateDTO {
    private String sku;
    private Integer estoque;
    private Long produtoId;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String imagemEsp;
    private Long corId;
    private Long tamanhoId;
}
