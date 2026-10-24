package com.erpmodas.dto.relatorios;

import com.erpmodas.enums.FormatoExportacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoRelatorioDTO {
    private String nomeArquivo;
    private FormatoExportacao formato;
    private byte[] conteudo;
}
