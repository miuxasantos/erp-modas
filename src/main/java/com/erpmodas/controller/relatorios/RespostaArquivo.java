package com.erpmodas.controller.relatorios;

import com.erpmodas.dto.relatorios.ArquivoRelatorioDTO;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

final class RespostaArquivo {
    private RespostaArquivo() {}

    static ResponseEntity<byte[]> de(ArquivoRelatorioDTO arquivo) {
        ContentDisposition disposicao = ContentDisposition.attachment()
                .filename(arquivo.getNomeArquivo())
                .build();

        return ResponseEntity.ok()
                .contentType(arquivo.getFormato().getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, disposicao.toString())
                // Relatório financeiro não deve ficar em cache de navegador/proxy
                .cacheControl(CacheControl.noStore())
                .body(arquivo.getConteudo());
    }
}
