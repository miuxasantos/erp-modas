package com.erpmodas.dto.apoio.variacaoProdutoDto;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.dto.produto.ProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariacaoProdutoResponseDTO {
    private Long id;
    private String sku;
    private Integer estoque;
    private ProdutoResponseDTO produto;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String imagemEsp;
    private CorDTO cor;
    private TamanhoDTO tamanho;
}
