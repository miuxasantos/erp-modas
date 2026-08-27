package com.erpmodas.dto.catalogo;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.dto.apoio.TamanhoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoCatalogoResponseDTO {

    private Long id;
    private CorDTO corDTO;
    private TamanhoDTO tamanhoDTO;
    private Boolean disponivel;
    private String imagemEsp;
}
