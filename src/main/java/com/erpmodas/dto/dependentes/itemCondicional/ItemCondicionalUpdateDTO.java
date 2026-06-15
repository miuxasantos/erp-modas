package com.erpmodas.dto.dependentes.itemCondicional;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCondicionalUpdateDTO {
    private Long variacaoProdutoId;
    private Integer quantidade;
}
