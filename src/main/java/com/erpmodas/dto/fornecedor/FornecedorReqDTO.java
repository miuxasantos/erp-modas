package com.erpmodas.dto.fornecedor;

import com.erpmodas.model.entidades.Assessoria;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorReqDTO {
    @NotNull
    private String nome;
    @NotNull
    private String contato;
    private Long assessoriaId;
}
