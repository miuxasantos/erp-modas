package com.erpmodas.dto.fornecedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorUpdateDTO {
    private String nome;
    private String contato;
    private String assessoria;
}
