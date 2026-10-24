package com.erpmodas.service.relatorios;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
public class GeradorPdf {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");
    // Explícito para o "Gerado em" não sair em UTC se o servidor de produção estiver em UTC
    private static final ZoneId FUSO_LOJA = ZoneId.of("America/Sao_Paulo");

    private final TemplateEngine templateEngine;
    private final String nomeEmpresa;

    public GeradorPdf(
            TemplateEngine templateEngine,
            @Value("${app.relatorios.empresa:ERP Modas}") String nomeEmpresa
    ) {
        this.templateEngine = templateEngine;
        this.nomeEmpresa = nomeEmpresa;
    }

    public byte[] gerar(String template, String titulo, Map<String, Object> variaveis) {
        // O Locale do Context é o que faz #numbers.formatCurrency sair como "R$ 1.234,56"
        Context contexto = new Context(PT_BR);
        contexto.setVariables(variaveis);
        contexto.setVariable("titulo", titulo);
        contexto.setVariable("empresa", nomeEmpresa);
        contexto.setVariable("geradoEm", LocalDateTime.now(FUSO_LOJA));

        String html = templateEngine.process(template, contexto);

        try (ByteArrayOutputStream saida = new ByteArrayOutputStream()) {
            new PdfRendererBuilder()
                    .withHtmlContent(html, null)
                    .toStream(saida)
                    .run();
            return saida.toByteArray();
        } catch (IOException | RuntimeException e) {
            log.error("Falha ao converter o template '{}' em PDF", template, e);
            throw new IllegalStateException("Não foi possível gerar o PDF do relatório.", e);
        }
    }

}
