package com.erpmodas.dto.dependentes.itemCompra;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraUpdateDTO {
    private Long variacaoProdutoId;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
