package com.erpmodas.dto.apoio;

import com.erpmodas.enums.Caimento;
import com.erpmodas.enums.Elasticidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecidoDTO {

    private Long id;
    private String nome;
    private Integer gramatura;
    private Caimento caimento;
    private Elasticidade elasticidade;
}
