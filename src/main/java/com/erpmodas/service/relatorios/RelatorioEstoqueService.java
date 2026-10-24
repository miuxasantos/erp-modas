package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.estoque.EstoqueGeralDTO;
import com.erpmodas.dto.relatorios.estoque.EstoqueProdutoDTO;
import com.erpmodas.dto.relatorios.estoque.EstoqueVariacaoDTO;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioEstoqueService {
    private final ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public EstoqueGeralDTO geral(boolean apenasAtivos) {
        List<EstoqueProdutoDTO> produtos =
                montarProdutos(apenasAtivos, null, null);

        int produtosComEstoque = 0;
        int variacoes = 0;
        int variacoesSemEstoque = 0;
        int unidades = 0;
        BigDecimal valorCusto = BigDecimal.ZERO;
        BigDecimal valorVenda = BigDecimal.ZERO;

        for (EstoqueProdutoDTO produto : produtos) {
            if (produto.getQuantidadeUnidades() > 0) {
                produtosComEstoque++;
            }

            variacoes += produto.getQuantidadeVariacoes();
            unidades += produto.getQuantidadeUnidades();
            valorCusto = valorCusto.add(
                    produto.getValorEstoqueCusto()
            );
            valorVenda = valorVenda.add(
                    produto.getValorEstoqueVenda()
            );

            for (EstoqueVariacaoDTO variacao : produto.getVariacoes()) {
                if (variacao.getQuantidade() <= 0) {
                    variacoesSemEstoque++;
                }
            }
        }

        return new EstoqueGeralDTO(
                produtos.size(),
                produtosComEstoque,
                variacoes,
                variacoesSemEstoque,
                unidades,
                valorCusto,
                valorVenda
        );
    }

    @Transactional(readOnly = true)
    public List<EstoqueProdutoDTO> produtos(boolean apenasAtivos) {
        return montarProdutos(apenasAtivos, null, null);
    }

    private List<EstoqueProdutoDTO> montarProdutos(
            boolean apenasAtivos,
            Boolean noCatalogo,
            Long categoriaId
    ) {
        List<Produto> produtos =
                produtoRepository.buscarParaRelatorioEstoque(
                        apenasAtivos,
                        noCatalogo,
                        categoriaId
                );

        List<EstoqueProdutoDTO> resultado =
                new ArrayList<>(produtos.size());

        for (Produto produto : produtos) {
            List<EstoqueVariacaoDTO> variacoes = new ArrayList<>();
            int unidades = 0;
            BigDecimal valorCusto = BigDecimal.ZERO;
            BigDecimal valorVenda = BigDecimal.ZERO;

            for (VariacaoProduto variacao : produto.getVariacoes()) {
                int quantidade = variacao.getEstoque();
                BigDecimal precoCusto = variacao.precoCustoFinal();
                BigDecimal precoVenda = variacao.precoVendaFinal();

                BigDecimal custoVariacao = precoCusto.multiply(
                        BigDecimal.valueOf(quantidade)
                );
                BigDecimal vendaVariacao = precoVenda.multiply(
                        BigDecimal.valueOf(quantidade)
                );

                unidades += quantidade;
                valorCusto = valorCusto.add(custoVariacao);
                valorVenda = valorVenda.add(vendaVariacao);

                variacoes.add(new EstoqueVariacaoDTO(
                        variacao.getId(),
                        variacao.getSku(),
                        variacao.getCor().getNome(),
                        variacao.getTamanho()
                                .getTamanho().getDescricao(),
                        quantidade,
                        precoCusto,
                        precoVenda,
                        custoVariacao,
                        vendaVariacao
                ));
            }

            variacoes.sort(
                    Comparator.comparing(EstoqueVariacaoDTO::getSku)
            );

            resultado.add(new EstoqueProdutoDTO(
                    produto.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getCategoria().getNome(),
                    Boolean.TRUE.equals(produto.getAtivo()),
                    variacoes.size(),
                    unidades,
                    valorCusto,
                    valorVenda,
                    variacoes
            ));
        }

        return resultado;
    }
}
