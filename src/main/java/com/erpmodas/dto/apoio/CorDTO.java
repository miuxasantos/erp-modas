package com.erpmodas.dto.apoio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorDTO {
    private Long id;
    private String nome;
    private String codigoHex;
}
