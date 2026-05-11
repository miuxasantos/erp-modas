package com.erpmodas.dto.dependentes.itemCompra;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.dto.compra.CompraDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraDTO {

    private Long id;
    private CompraDTO compra;
    private VariacaoProdutoDTO variacaoProduto;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
