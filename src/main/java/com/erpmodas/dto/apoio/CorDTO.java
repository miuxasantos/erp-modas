package com.erpmodas.dto.apoio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//trocar para record
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorDTO {
    private Long id;
    private String nome;
    private String codigoHex;
}
