package com.erpmodas.dto.limpeza;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimpezaResultDTO {
    private Integer arquivosDisco;
    private Integer referenciasBanco;
    private Integer orfaosEncontrados;
    private Integer removidos;
    private Long bytesLiberados;
    private List<String> caminhos;

}
