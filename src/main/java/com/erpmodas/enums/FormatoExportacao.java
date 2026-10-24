package com.erpmodas.enums;

import lombok.Getter;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

@Getter
public enum FormatoExportacao {
    PDF("pdf", MediaType.APPLICATION_PDF),
    CSV("csv", new MediaType("text", "csv", StandardCharsets.UTF_8));

    private final String extensao;
    private final MediaType mediaType;

    FormatoExportacao(String extensao, MediaType mediaType) {
        this.extensao = extensao;
        this.mediaType = mediaType;
    }
}
