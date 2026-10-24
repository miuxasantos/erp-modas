package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.ArquivoRelatorioDTO;
import com.erpmodas.dto.relatorios.compra.CompraResumoDTO;
import com.erpmodas.dto.relatorios.compra.ComprasPorPeriodoDTO;
import com.erpmodas.dto.relatorios.compra.FornecedorMaisComprasDTO;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.service.relatorios.CsvBuilder;
import com.erpmodas.service.relatorios.GeradorPdf;
import com.erpmodas.service.relatorios.RelatorioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioCompraExportacaoService {

    private static final String TEMPLATE_COMPRAS = "relatorios/compras";
    private static final String TEMPLATE_FORNECEDORES = "relatorios/fornecedores";

    private final RelatorioCompraService relatorioCompraService;
    private final GeradorPdf geradorPdf;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // COMPRAS POR PERÍODO
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportarPorPeriodo(FormatoExportacao formato, LocalDate inicio, LocalDate fim) {
        ComprasPorPeriodoDTO relatorio = relatorioCompraService.porPeriodo(inicio, fim);

        byte[] conteudo = switch (formato) {
            case PDF -> gerarPdfCompras("Relatório de Compras", relatorio.getCompras(), relatorio, inicio, fim);
            case CSV -> gerarCsvCompras(relatorio.getCompras());
        };

        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-compras", RelatorioUtils.sufixoPeriodo(inicio, fim), formato),
                formato,
                conteudo
        );
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // COMPRAS COM MAIS PRODUTOS
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** Mesmas colunas de compras por período, ordenadas por unidades (ordem vem do service) */
    public ArquivoRelatorioDTO exportarComMaisProdutos(FormatoExportacao formato, LocalDate inicio, LocalDate fim) {
        List<CompraResumoDTO> compras = relatorioCompraService.comMaisProdutos(inicio, fim);

        byte[] conteudo = switch (formato) {
            case PDF -> gerarPdfCompras("Compras com Mais Produtos", compras, null, inicio, fim);
            case CSV -> gerarCsvCompras(compras);
        };

        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-compras-mais-produtos", RelatorioUtils.sufixoPeriodo(inicio, fim), formato),
                formato,
                conteudo
        );
    }

    private byte[] gerarPdfCompras(
            String titulo,
            List<CompraResumoDTO> compras,
            ComprasPorPeriodoDTO resumo,
            LocalDate inicio,
            LocalDate fim
    ) {
        // HashMap em vez de Map.of: Map.of não aceita valor nulo, e "resumo" é opcional
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("subtitulo", "Período: " + RelatorioUtils.descricaoPeriodo(inicio, fim));
        variaveis.put("compras", compras);
        variaveis.put("resumo", resumo);
        variaveis.put("totalUnidades", compras.stream().mapToInt(CompraResumoDTO::getUnidadesAdicionadas).sum());
        variaveis.put("totalValor", compras.stream()
                .map(CompraResumoDTO::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return geradorPdf.gerar(TEMPLATE_COMPRAS, titulo, variaveis);
    }

    private byte[] gerarCsvCompras(List<CompraResumoDTO> compras) {
        CsvBuilder csv = new CsvBuilder()
                .linha("Nº compra", "Lote", "Data de chegada", "Fornecedor",
                        "Unidades", "Produtos distintos", "Valor total");

        for (CompraResumoDTO compra : compras) {
            csv.linha(
                    compra.getId(),
                    compra.getLote(),
                    compra.getDataChegada(),
                    compra.getFornecedor(),
                    compra.getUnidadesAdicionadas(),
                    compra.getProdutosDistintos(),
                    compra.getValorTotal()
            );
        }
        return csv.toBytes();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // FORNECEDORES
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportarFornecedores(FormatoExportacao formato, LocalDate inicio, LocalDate fim) {
        List<FornecedorMaisComprasDTO> fornecedores = relatorioCompraService.fornecedores(inicio, fim);

        byte[] conteudo = switch (formato) {
            case PDF -> geradorPdf.gerar(TEMPLATE_FORNECEDORES, "Compras por Fornecedor", Map.of(
                    "subtitulo", "Período: " + RelatorioUtils.descricaoPeriodo(inicio, fim),
                    "fornecedores", fornecedores,
                    "totalCompras", fornecedores.stream().mapToInt(FornecedorMaisComprasDTO::getQuantidadeCompras).sum(),
                    "totalUnidades", fornecedores.stream().mapToInt(FornecedorMaisComprasDTO::getUnidadesAdicionadas).sum(),
                    "totalValor", fornecedores.stream()
                            .map(FornecedorMaisComprasDTO::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            ));
            case CSV -> gerarCsvFornecedores(fornecedores);
        };

        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-fornecedores", RelatorioUtils.sufixoPeriodo(inicio, fim), formato),
                formato,
                conteudo
        );
    }

    private byte[] gerarCsvFornecedores(List<FornecedorMaisComprasDTO> fornecedores) {
        CsvBuilder csv = new CsvBuilder()
                .linha("Fornecedor", "Compras", "Unidades", "Valor total");

        for (FornecedorMaisComprasDTO fornecedor : fornecedores) {
            csv.linha(
                    fornecedor.getFornecedor(),
                    fornecedor.getQuantidadeCompras(),
                    fornecedor.getUnidadesAdicionadas(),
                    fornecedor.getValorTotal()
            );
        }
        return csv.toBytes();
    }
}