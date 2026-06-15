package com.erpmodas.dto.dependentes.itemCompra;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraResponseDTO {

    private Long id;
    private VariacaoProdutoResponseDTO variacaoProduto;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
