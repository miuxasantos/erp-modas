package com.erpmodas.dto.usuario;

import com.erpmodas.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private Boolean status;
    private LocalDateTime ultimoAcesso;
    private Cargo cargo;
}
