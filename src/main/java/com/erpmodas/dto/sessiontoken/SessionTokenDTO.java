package com.erpmodas.dto.sessiontoken;

import com.erpmodas.dto.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionTokenDTO {
    private Long id;
    private String token;
    private UsuarioDTO usuario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExp;
    private Boolean ativo;
}
