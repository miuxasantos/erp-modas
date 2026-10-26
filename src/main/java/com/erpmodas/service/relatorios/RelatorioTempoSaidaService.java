package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.estoque.TempoSaidaDTO;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.repository.apoio.VariacaoProdutoRepository;
import com.erpmodas.repository.dependentes.ItemCompraRepository;
import com.erpmodas.repository.dependentes.ItemVendaRepository;
import com.erpmodas.repository.MovimentoEstoqueProjecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioTempoSaidaService {

    private final ItemVendaRepository itemVendaRepository;
    private final ItemCompraRepository itemCompraRepository;
    private final VariacaoProdutoRepository variacaoProdutoRepository;

    @Transactional(readOnly = true)
    public TempoSaidaDTO gerar(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data inicial deve ser anterior ou igual à data final."
            );
        }

        List<Long> idsVariacoes = itemVendaRepository.buscarVariacoesVendidasEntre(inicio, fim);
        if (idsVariacoes.isEmpty()) {
            return new TempoSaidaDTO(0, null);
        }

        // 3 consultas no total, independente de quantas variações: nada de consulta dentro do laço
        Map<Long, VariacaoProduto> variacoes = variacaoProdutoRepository.buscarComProduto(idsVariacoes)
                .stream()
                .collect(Collectors.toMap(VariacaoProduto::getId, Function.identity()));
        Map<Long, List<MovimentoEstoqueProjecao>> entradas = agruparPorVariacao(
                itemCompraRepository.buscarEntradasDasVariacoes(idsVariacoes));
        Map<Long, List<MovimentoEstoqueProjecao>> saidas = agruparPorVariacao(
                itemVendaRepository.buscarSaidasDasVariacoes(idsVariacoes));

        int pecas = 0;
        long somaDias = 0;

        for (Long idVariacao : idsVariacoes) {
            VariacaoProduto variacao = variacoes.get(idVariacao);
            LocalDate dataInclusao = variacao.getProduto().getDataInclusao();
            List<MovimentoEstoqueProjecao> saidasVariacao = saidas.getOrDefault(idVariacao, List.of());

            Deque<Lote> lotes = montarLotes(variacao, entradas.getOrDefault(idVariacao, List.of()), saidasVariacao);

            for (MovimentoEstoqueProjecao saida : saidasVariacao) {
                // Saídas estão em ordem de data: depois do fim do período não há mais nada a contar
                if (saida.getData().isAfter(fim)) {
                    break;
                }
                boolean noPeriodo = !saida.getData().isBefore(inicio);
                int restante = saida.getQuantidade();

                while (restante > 0) {
                    Lote lote = lotes.peekFirst();
                    LocalDate dataEntrada;
                    int quantidade;

                    if (lote == null) {
                        // Mais saídas do que entradas registradas (ex.: estoque ajustado à mão):
                        // considera que a peça estava na loja desde a inclusão do produto
                        dataEntrada = dataInclusao != null ? dataInclusao : saida.getData();
                        quantidade = restante;
                    } else {
                        dataEntrada = lote.data;
                        quantidade = Math.min(restante, lote.saldo);
                        lote.saldo -= quantidade;
                        if (lote.saldo == 0) {
                            lotes.pollFirst();
                        }
                    }
                    restante -= quantidade;

                    if (noPeriodo) {
                        // Compra lançada com data de chegada depois da venda daria tempo negativo: conta como 0
                        long dias = Math.max(0, ChronoUnit.DAYS.between(dataEntrada, saida.getData()));
                        pecas += quantidade;
                        somaDias += dias * quantidade;
                    }
                }
            }
        }

        // Média ponderada por peça: uma venda de 5 peças pesa 5 vezes mais que uma de 1
        BigDecimal media = pecas == 0
                ? null
                : BigDecimal.valueOf(somaDias).divide(BigDecimal.valueOf(pecas), 1, RoundingMode.HALF_UP);

        return new TempoSaidaDTO(pecas, media);
    }

    private Deque<Lote> montarLotes(
            VariacaoProduto variacao,
            List<MovimentoEstoqueProjecao> entradas,
            List<MovimentoEstoqueProjecao> saidas
    ) {
        Deque<Lote> lotes = new ArrayDeque<>();

        int totalComprado = entradas.stream().mapToInt(MovimentoEstoqueProjecao::getQuantidade).sum();
        int totalVendido = saidas.stream().mapToInt(MovimentoEstoqueProjecao::getQuantidade).sum();
        int estoqueInicial = variacao.getEstoque() + totalVendido - totalComprado;

        LocalDate dataInclusao = variacao.getProduto().getDataInclusao();
        if (estoqueInicial > 0 && dataInclusao != null) {
            lotes.add(new Lote(dataInclusao, estoqueInicial));
        }

        for (MovimentoEstoqueProjecao entrada : entradas) {
            lotes.add(new Lote(entrada.getData(), entrada.getQuantidade()));
        }
        return lotes;
    }

    private static Map<Long, List<MovimentoEstoqueProjecao>> agruparPorVariacao(List<MovimentoEstoqueProjecao> movimentos) {
        // groupingBy mantém a ordem da lista original dentro de cada grupo (a ordem cronológica da consulta)
        return movimentos.stream().collect(Collectors.groupingBy(MovimentoEstoqueProjecao::getVariacaoId));
    }

    private static class Lote {
        private final LocalDate data;
        private int saldo;

        private Lote(LocalDate data, int saldo) {
            this.data = data;
            this.saldo = saldo;
        }
    }
}