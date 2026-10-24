package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.ArquivoRelatorioDTO;
import com.erpmodas.dto.relatorios.financeiro.ContasPeriodoDTO;
import com.erpmodas.dto.relatorios.financeiro.DiaFiscalDTO;
import com.erpmodas.dto.relatorios.financeiro.FaturamentoPeriodoDTO;
import com.erpmodas.dto.relatorios.financeiro.RelatorioFiscalMensalDTO;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.service.relatorios.CsvBuilder;
import com.erpmodas.service.relatorios.GeradorPdf;
import com.erpmodas.service.relatorios.RelatorioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioFinanceiroExportacaoService {

    private static final String TEMPLATE_RESUMO = "relatorios/financeiro-resumo";
    private static final String TEMPLATE_FISCAL = "relatorios/fiscal";
    private static final DateTimeFormatter MES_ANO =
            DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));

    private final RelatorioFinanceiroService relatorioFinanceiroService;
    private final GeradorPdf geradorPdf;

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // RESUMO FINANCEIRO (faturamento + contas a pagar + contas a receber)
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportarResumo(FormatoExportacao formato, LocalDate inicio, LocalDate fim) {
        FaturamentoPeriodoDTO faturamento = relatorioFinanceiroService.faturamento(inicio, fim);
        ContasPeriodoDTO contasReceber = relatorioFinanceiroService.contasAReceber(inicio, fim);
        ContasPeriodoDTO contasPagar = relatorioFinanceiroService.contasAPagar(inicio, fim);

        byte[] conteudo = switch (formato) {
            case PDF -> geradorPdf.gerar(TEMPLATE_RESUMO, "Resumo Financeiro", Map.of(
                    "subtitulo", "Período: " + RelatorioUtils.descricaoPeriodo(inicio, fim),
                    "faturamento", faturamento,
                    "contasReceber", contasReceber,
                    "contasPagar", contasPagar
            ));
            case CSV -> gerarCsvResumo(faturamento, contasReceber, contasPagar);
        };

        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-financeiro", RelatorioUtils.sufixoPeriodo(inicio, fim), formato),
                formato,
                conteudo
        );
    }

    private byte[] gerarCsvResumo(
            FaturamentoPeriodoDTO faturamento,
            ContasPeriodoDTO contasReceber,
            ContasPeriodoDTO contasPagar
    ) {
        return new CsvBuilder()
                .linha("Indicador", "Quantidade", "Valor total", "Quitado", "Em aberto")
                .linha("Vendas (faturamento)", faturamento.getQuantidadeVendas(), faturamento.getFaturamento(), null, null)
                .linha("Contas a receber", contasReceber.getQuantidadeContas(), contasReceber.getValorTotal(),
                        contasReceber.getValorPago(), contasReceber.getValorEmAberto())
                .linha("Contas a pagar", contasPagar.getQuantidadeContas(), contasPagar.getValorTotal(),
                        contasPagar.getValorPago(), contasPagar.getValorEmAberto())
                .toBytes();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // FISCAL MENSAL
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public ArquivoRelatorioDTO exportarFiscal(FormatoExportacao formato, int ano, int mes) {
        // O service valida ano/mês (400 se inválido) antes do YearMonth.of abaixo
        RelatorioFiscalMensalDTO relatorio = relatorioFinanceiroService.fiscal(ano, mes);
        YearMonth competencia = YearMonth.of(ano, mes);

        byte[] conteudo = switch (formato) {
            case PDF -> geradorPdf.gerar(TEMPLATE_FISCAL, "Relatório Fiscal Mensal", Map.of(
                    "subtitulo", "Competência: " + competencia.format(MES_ANO),
                    "relatorio", relatorio
            ));
            case CSV -> gerarCsvFiscal(relatorio);
        };

        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-fiscal", competencia.toString(), formato),
                formato,
                conteudo
        );
    }

    /**
     * Só o livro diário (uma linha por dia). A lista de despesas fica apenas no PDF:
     * duas tabelas diferentes no mesmo CSV quebram filtro e tabela dinâmica no Excel.
     */
    private byte[] gerarCsvFiscal(RelatorioFiscalMensalDTO relatorio) {
        CsvBuilder csv = new CsvBuilder()
                .linha("Data",
                        "Entrada dinheiro", "Entrada Pix", "Entrada cartão", "Entrada outros", "Total entradas",
                        "Saída dinheiro", "Saída Pix", "Saída cartão", "Saída outros", "Total saídas",
                        "Saldo");

        for (DiaFiscalDTO dia : relatorio.getDias()) {
            csv.linha(
                    dia.getData(),
                    dia.getEntradaDinheiro(), dia.getEntradaPix(), dia.getEntradaCartao(),
                    dia.getEntradaOutros(), dia.getTotalEntradas(),
                    dia.getSaidaDinheiro(), dia.getSaidaPix(), dia.getSaidaCartao(),
                    dia.getSaidaOutros(), dia.getTotalSaidas(),
                    dia.getSaldo()
            );
        }
        return csv.toBytes();
    }
}