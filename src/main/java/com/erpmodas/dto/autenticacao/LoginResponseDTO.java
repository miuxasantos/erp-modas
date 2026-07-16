package com.erpmodas.dto.autenticacao;

import com.erpmodas.dto.usuario.UsuarioResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String tipo;         // sempre "Bearer" — indica o tipo do token
    private UsuarioResponseDTO usuarioResponseDTO;
    private Long expiraEmSegundos;   // opcional: quanto tempo até expirar
}