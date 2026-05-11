package com.erpmodas.dto.dependentes.itemCondicional;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.dto.condicional.CondicionalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCondicionalDTO {
    private Long id;
    private VariacaoProdutoDTO variacaoProduto;
    private Integer quantidade;
    private CondicionalDTO condicional;
}
