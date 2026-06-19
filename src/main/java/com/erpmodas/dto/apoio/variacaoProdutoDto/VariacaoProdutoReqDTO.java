package com.erpmodas.dto.apoio.variacaoProdutoDto;

import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.model.entidades.apoio.Tamanho;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProdutoReqDTO {
    @NotNull
    private String sku;
    @NotNull
    private Integer estoque;
    @NotNull
    private Long produtoId;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String imagemEsp;
    @NotNull
    private Long corId;
    @NotNull
    private Long tamanhoId;
}
