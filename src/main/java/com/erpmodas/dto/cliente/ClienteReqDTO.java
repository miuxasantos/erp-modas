package com.erpmodas.dto.cliente;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteReqDTO {
    @NotNull
    private String nome;
    @NotNull
    private String contato;
    @NotNull
    private String documento;
    private String numero;
    private String rua;
    private String bairro;
    private String cidade;
}
