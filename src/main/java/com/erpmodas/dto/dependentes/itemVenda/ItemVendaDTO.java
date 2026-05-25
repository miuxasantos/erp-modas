package com.erpmodas.dto.dependentes.itemVenda;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaDTO {
    private Long id;
    private Long variacaoProdutoId;
    private VariacaoProdutoDTO variacaoProduto;
    private BigDecimal valorUnit;
    private Integer quantidade;
    private BigDecimal subTotal;
}
