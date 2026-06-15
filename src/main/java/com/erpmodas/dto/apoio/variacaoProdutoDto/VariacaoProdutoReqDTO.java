package com.erpmodas.dto.apoio.variacaoProdutoDto;

import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.model.entidades.apoio.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProdutoReqDTO {
    private String sku;
    private Integer estoque;
    private Long produtoId;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String imagemEsp;
    private Long corId;
    private Long tamanhoId;
}
