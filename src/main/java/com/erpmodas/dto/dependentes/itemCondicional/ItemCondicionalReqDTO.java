package com.erpmodas.dto.dependentes.itemCondicional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCondicionalReqDTO {
    private Long variacaoProdutoId;
    private Integer quantidade;
}
