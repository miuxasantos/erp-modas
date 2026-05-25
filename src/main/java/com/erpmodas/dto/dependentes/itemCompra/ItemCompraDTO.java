package com.erpmodas.dto.dependentes.itemCompra;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraDTO {

    private Long id;
    private Long variacaoProdutoId;
    private VariacaoProdutoDTO variacaoProduto;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
