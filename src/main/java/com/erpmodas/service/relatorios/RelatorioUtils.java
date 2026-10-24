package com.erpmodas.service.relatorios;

import com.erpmodas.enums.FormatoExportacao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class RelatorioUtils {
    private static final DateTimeFormatter DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final ZoneId FUSO_LOJA = ZoneId.of("America/Sao_Paulo");

    private RelatorioUtils() {}

    /** Ex.: "01/10/2026 a 31/10/2026", "a partir de 01/10/2026", "todo o histórico" */
    public static String descricaoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return inicio.format(DATA_BR) + " a " + fim.format(DATA_BR);
        }
        if (inicio != null) {
            return "a partir de " + inicio.format(DATA_BR);
        }
        if (fim != null) {
            return "até " + fim.format(DATA_BR);
        }
        return "todo o histórico";
    }

    /** Ex.: "2026-10-01_a_2026-10-31", "desde_2026-10-01", "todo-historico" */
    public static String sufixoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return inicio + "_a_" + fim;
        }
        if (inicio != null) {
            return "desde_" + inicio;
        }
        if (fim != null) {
            return "ate_" + fim;
        }
        return "todo-historico";
    }

    /** Data de hoje no fuso da loja, para relatórios que são uma "foto" do momento (ex.: estoque) */
    public static LocalDate hoje() {
        return LocalDate.now(FUSO_LOJA);
    }

    /** Ex.: nomeArquivo("relatorio-compras", "2026-10-01_a_2026-10-31", PDF) -> relatorio-compras_2026-10-01_a_2026-10-31.pdf */
    public static String nomeArquivo(String base, String sufixo, FormatoExportacao formato) {
        return base + "_" + sufixo + "." + formato.getExtensao();
    }
}
