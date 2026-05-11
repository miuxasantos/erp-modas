package com.erpmodas.dto.especial;

import com.erpmodas.dto.usuario.UsuarioDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.model.entidades.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaDTO {
    private Long id;
    private UsuarioDTO usuario;
    private TipoAcaoAud tipoAcaoAud;
    private String entidade;
    private Long entidadeId;
    private LocalDateTime dataHora;
}