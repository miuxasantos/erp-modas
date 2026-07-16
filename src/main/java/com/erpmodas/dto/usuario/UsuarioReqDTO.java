package com.erpmodas.dto.usuario;

import com.erpmodas.enums.Cargo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 8, max = 100, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
        message = "Senha deve conter pelo menos uma letra minúscula, uma maiúscula e um número"
    )
    private String senha;
    @NotNull
    private Cargo cargo;
}
