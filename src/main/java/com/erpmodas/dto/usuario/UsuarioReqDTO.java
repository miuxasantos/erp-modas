package com.erpmodas.dto.usuario;

import com.erpmodas.enums.Cargo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioReqDTO {
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private Boolean status;
    private String senha;
    @NotNull
    private Cargo cargo;
}
