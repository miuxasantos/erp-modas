package com.erpmodas.dto.catalogo;

import com.erpmodas.enums.Caimento;
import com.erpmodas.enums.Elasticidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecidoCatalogoDTO {
    private String nome;
    private Caimento caimento;
    private Elasticidade elasticidade;
}
