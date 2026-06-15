package com.erpmodas.dto.dependentes.itemCondicional;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCondicionalResponseDTO {
    private VariacaoProdutoResponseDTO variacaoProduto;
    private Integer quantidade;
}
