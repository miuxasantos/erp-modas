package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.ArquivoRelatorioDTO;
import com.erpmodas.dto.relatorios.estoque.EstoqueProdutoDTO;
import com.erpmodas.dto.relatorios.estoque.EstoqueVariacaoDTO;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.service.relatorios.CsvBuilder;
import com.erpmodas.service.relatorios.GeradorPdf;
import com.erpmodas.service.relatorios.RelatorioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioEstoqueExportacaoService {

    private static final String TEMPLATE = "relatorios/estoque";

    private final RelatorioEstoqueService relatorioEstoqueService;
    private final GeradorPdf geradorPdf;

    public ArquivoRelatorioDTO exportar(FormatoExportacao formato, boolean apenasAtivos) {
        List<EstoqueProdutoDTO> produtos = relatorioEstoqueService.produtos(apenasAtivos);

        byte[] conteudo = switch (formato) {
            case PDF -> geradorPdf.gerar(TEMPLATE, "Relatório de Estoque", Map.of(
                    "subtitulo", apenasAtivos ? "Somente produtos ativos" : "Produtos ativos e inativos",
                    // Totais vêm do próprio service para a regra (ex.: o que conta como "sem estoque") ficar num lugar só
                    "geral", relatorioEstoqueService.geral(apenasAtivos),
                    "produtos", produtos
            ));
            case CSV -> gerarCsv(produtos);
        };

        // Estoque é uma "foto" do momento, então o nome do arquivo leva a data de geração
        return new ArquivoRelatorioDTO(
                RelatorioUtils.nomeArquivo("relatorio-estoque", RelatorioUtils.hoje().toString(), formato),
                formato,
                conteudo
        );
    }

    /**
     * Uma linha por variação, repetindo os dados do produto: formato "achatado"
     * é o que o Excel precisa para filtrar e montar tabela dinâmica.
     */
    private byte[] gerarCsv(List<EstoqueProdutoDTO> produtos) {
        CsvBuilder csv = new CsvBuilder()
                .linha("Código", "Produto", "Categoria", "Ativo", "SKU", "Cor", "Tamanho",
                        "Quantidade", "Preço de custo", "Preço de venda",
                        "Total custo", "Total venda");

        for (EstoqueProdutoDTO produto : produtos) {
            if (produto.getVariacoes().isEmpty()) {
                // Produto sem variação ainda aparece, para não sumir do relatório
                csv.linha(produto.getCodigo(), produto.getNome(), produto.getCategoria(), produto.isAtivo(),
                        null, null, null, 0, null, null, null, null);
                continue;
            }

            for (EstoqueVariacaoDTO variacao : produto.getVariacoes()) {
                csv.linha(
                        produto.getCodigo(),
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.isAtivo(),
                        variacao.getSku(),
                        variacao.getCor(),
                        variacao.getTamanho(),
                        variacao.getQuantidade(),
                        variacao.getPrecoCusto(),
                        variacao.getPrecoVenda(),
                        variacao.getValorEstoqueCusto(),
                        variacao.getValorEstoqueVenda()
                );
            }
        }
        return csv.toBytes();
    }
}