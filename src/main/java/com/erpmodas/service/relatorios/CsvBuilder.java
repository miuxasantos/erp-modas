package com.erpmodas.service.relatorios;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CsvBuilder {

    private static final char SEPARADOR = ';';
    private static final String QUEBRA_LINHA = "\r\n";
    // Sem o BOM o Excel assume a codificação do Windows (ANSI) e quebra os acentos
    private static final String BOM_UTF8 = "\uFEFF";
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    // Caracteres que fazem o Excel interpretar a célula como fórmula (CSV Injection - OWASP)
    private static final String INICIO_DE_FORMULA = "=+-@\t\r";

    private final StringBuilder conteudo = new StringBuilder(BOM_UTF8);

    public CsvBuilder linha(Object... valores) {
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) {
                conteudo.append(SEPARADOR);
            }
            conteudo.append(celula(valores[i]));
        }
        conteudo.append(QUEBRA_LINHA);
        return this;
    }

    public byte[] toBytes() {
        return conteudo.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String celula(Object valor) {
        return switch (valor) {
            case null -> "";
            // Sem separador de milhar: "1234,56" o Excel reconhece como número; "1.234,56" às vezes vira texto
            case BigDecimal numero -> numero.toPlainString().replace('.', ',');
            case Integer numero -> numero.toString();
            case Long numero -> numero.toString();
            case Number numero -> new BigDecimal(numero.toString()).toPlainString().replace('.', ',');
            case LocalDate data -> data.format(DATA);
            case LocalDateTime dataHora -> dataHora.format(DATA_HORA);
            case Boolean booleano -> booleano ? "Sim" : "Não";
            default -> texto(valor.toString());
        };
    }

    private String texto(String valor) {
        String texto = valor;

        // Um nome de cliente digitado como "=HYPERLINK(...)" seria executado pelo Excel.
        // O apóstrofo força a célula a ser tratada como texto.
        if (!texto.isEmpty() && INICIO_DE_FORMULA.indexOf(texto.charAt(0)) >= 0) {
            texto = "'" + texto;
        }

        boolean precisaAspas = texto.indexOf(SEPARADOR) >= 0
                || texto.indexOf('"') >= 0
                || texto.indexOf('\n') >= 0
                || texto.indexOf('\r') >= 0;

        if (!precisaAspas) {
            return texto;
        }
        // RFC 4180: aspas internas são duplicadas e o campo inteiro vai entre aspas
        return '"' + texto.replace("\"", "\"\"") + '"';
    }
}
