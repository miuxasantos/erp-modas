package com.erpmodas.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String contato;
    private String documento;
    private String numero;
    private String rua;
    private String bairro;
    private String cidade;
}
