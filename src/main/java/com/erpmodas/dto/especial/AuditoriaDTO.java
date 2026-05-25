package com.erpmodas.dto.especial;

import com.erpmodas.enums.TipoAcaoAud;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaDTO {
    private Long id;
    private Long usuarioId;
    private TipoAcaoAud tipoAcaoAud;
    private String entidade;
    private Long entidadeId;
    private LocalDateTime dataHora;
}