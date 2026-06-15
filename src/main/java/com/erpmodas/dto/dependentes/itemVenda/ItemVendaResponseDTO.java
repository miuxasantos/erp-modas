package com.erpmodas.dto.dependentes.itemVenda;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaResponseDTO {
    private Long id;
    private VariacaoProdutoResponseDTO variacaoProduto;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
