package com.erpmodas.dto.apoio;

import com.erpmodas.dto.produto.ProdutoDTO;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.model.entidades.apoio.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariacaoProdutoDTO {
    private Long id;
    private String sku;
    private Integer estoque;
    private Long produtoId;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String imagemEsp;
    private Long corId;
    private Long tamanhoId;
}
