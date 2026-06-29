package com.erpmodas.dto.marca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcaDTO {

    private Long id;
    private String nome;
    private String observacoes;
}
