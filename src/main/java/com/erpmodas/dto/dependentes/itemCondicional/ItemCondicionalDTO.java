package com.erpmodas.dto.dependentes.itemCondicional;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCondicionalDTO {
    private Long id;
    private Long variacaoProdutoId;
    private VariacaoProdutoDTO variacaoProduto;
    private Integer quantidade;
}
