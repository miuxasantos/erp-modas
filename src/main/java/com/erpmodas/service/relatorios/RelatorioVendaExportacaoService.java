package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.ArquivoRelatorioDTO;
import com.erpmodas.dto.relatorios.venda.RelatoriosVendaDTO;
import com.erpmodas.dto.relatorios.venda.VendaAgrupadaDTO;
import com.erpmodas.dto.relatorios.venda.VendaRelatorioItemDTO;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.service.relatorios.RelatorioVendaAgrupadaService.Tipo;
import com.erpmodas.service.relatorios.CsvBuilder;
import com.erpmodas.service.relatorios.GeradorPdf;
import com.erpmodas.service.relatorios.RelatorioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Reaproveita as mesmas DTOs dos endpoints JSON: a regra de negócio de cada
 * relatório fica num lugar só, aqui muda apenas a apresentação.
 *
 * Sem @Transactional de propósito: os dados já chegam prontos nas DTOs,
 * então a conexão com o banco não fica presa enquanto o PDF é renderizado.
 */
@Service
@RequiredArgsConstructor
public class RelatorioVendaExportacaoService {

    private static final String TEMPLATE_PERIODO = "relatorios/vendas";
    private static final String TEMPLATE_AGRUPADO = "relatorios/vendas-agrupadas";

    private final RelatorioVendaService relatorioVendaService;
    private final RelatorioVendaAgrupadaService relatorioVendaAgrupadaService;
    private final GeradorPdf geradorPdf;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // VENDAS POR PERÍODO
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportar(FormatoExportacao formato, LocalDate inicio, LocalDate fim) {
        RelatoriosVendaDTO relatorio = relatorioVendaService.gerar(inicio, fim);

        // Switch exaustivo: se um formato novo entrar no enum, isso para de compilar até ser tratado aqui
        byte[] conteudo = switch (formato) {
            case PDF -> geradorPdf.gerar(TEMPLATE_PERIODO, "Relatório de Vendas", Map.of("relatorio", relatorio));
            case CSV -> gerarCsv(relatorio);
        };

        String nomeArquivo = RelatorioUtils.nomeArquivo(
                "relatorio-vendas", RelatorioUtils.sufixoPeriodo(inicio, fim), formato);
        return new ArquivoRelatorioDTO(nomeArquivo, formato, conteudo);
    }

    /**
     * Só as linhas, sem bloco de resumo: CSV é para análise no Excel
     * (filtro, tabela dinâmica), e linhas de total no meio dos dados atrapalham isso.
     */
    private byte[] gerarCsv(RelatoriosVendaDTO relatorio) {
        CsvBuilder csv = new CsvBuilder()
                .linha("Nº venda", "Data", "Cliente", "Forma de pagamento", "Valor total");

        for (VendaRelatorioItemDTO venda : relatorio.getVendas()) {
            csv.linha(
                    venda.getId(),
                    venda.getData(),
                    venda.getCliente(),
                    venda.getFormaPagamento() != null ? venda.getFormaPagamento().getDescricao() : null,
                    venda.getValorTotal()
            );
        }
        return csv.toBytes();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // VENDAS AGRUPADAS
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportarAgrupado(
            Tipo tipo, FormatoExportacao formato, LocalDate inicio, LocalDate fim
    ) {
        List<VendaAgrupadaDTO> grupos = relatorioVendaAgrupadaService.gerar(tipo, inicio, fim);

        byte[] conteudo = switch (formato) {
            case PDF -> gerarPdfAgrupado(tipo, grupos, inicio, fim);
            case CSV -> gerarCsvAgrupado(tipo, grupos);
        };

        String nomeArquivo = RelatorioUtils.nomeArquivo(
                "relatorio-vendas-por-" + tipo.name().toLowerCase(),
                RelatorioUtils.sufixoPeriodo(inicio, fim),
                formato
        );
        return new ArquivoRelatorioDTO(nomeArquivo, formato, conteudo);
    }

    private byte[] gerarPdfAgrupado(Tipo tipo, List<VendaAgrupadaDTO> grupos, LocalDate inicio, LocalDate fim) {
        int totalQuantidade = grupos.stream().mapToInt(VendaAgrupadaDTO::getQuantidade).sum();
        BigDecimal totalFaturamento = grupos.stream()
                .map(VendaAgrupadaDTO::getFaturamento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String subtitulo = "Período: " + RelatorioUtils.descricaoPeriodo(inicio, fim);
        if (tipo == Tipo.CATALOGO) {
            subtitulo += " · Apenas produtos ativos marcados para o catálogo";
        }

        return geradorPdf.gerar(TEMPLATE_AGRUPADO, tituloAgrupado(tipo), Map.of(
                "subtitulo", subtitulo,
                "grupos", grupos,
                "rotuloGrupo", rotuloGrupo(tipo),
                "rotuloQuantidade", rotuloQuantidade(tipo),
                // Estações saem em ordem cronológica, então a coluna de posição não faz sentido
                "ranking", tipo != Tipo.ESTACOES,
                "totalQuantidade", totalQuantidade,
                "totalFaturamento", totalFaturamento
        ));
    }

    private byte[] gerarCsvAgrupado(Tipo tipo, List<VendaAgrupadaDTO> grupos) {
        CsvBuilder csv = new CsvBuilder()
                .linha(rotuloGrupo(tipo), rotuloQuantidade(tipo), "Faturamento");

        for (VendaAgrupadaDTO grupo : grupos) {
            csv.linha(grupo.getNome(), grupo.getQuantidade(), grupo.getFaturamento());
        }
        return csv.toBytes();
    }

    private static String tituloAgrupado(Tipo tipo) {
        return switch (tipo) {
            case CLIENTES -> "Vendas por Cliente";
            case PRODUTOS -> "Vendas por Produto";
            case CATALOGO -> "Vendas de Produtos do Catálogo";
            case ESTACOES -> "Vendas por Estação";
            case CORES -> "Vendas por Cor";
            case TAMANHOS -> "Vendas por Tamanho";
            case CATEGORIAS -> "Vendas por Categoria";
        };
    }

    private static String rotuloGrupo(Tipo tipo) {
        return switch (tipo) {
            case CLIENTES -> "Cliente";
            case PRODUTOS, CATALOGO -> "Produto";
            case ESTACOES -> "Estação";
            case CORES -> "Cor";
            case TAMANHOS -> "Tamanho";
            case CATEGORIAS -> "Categoria";
        };
    }

    /**
     * No RelatorioVendaAgrupadaService, clientes e estações somam 1 por VENDA;
     * os demais agrupamentos somam a quantidade de cada ITEM vendido.
     */
    private static String rotuloQuantidade(Tipo tipo) {
        return (tipo == Tipo.CLIENTES || tipo == Tipo.ESTACOES) ? "Vendas" : "Peças vendidas";
    }
}