package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.venda.VendaAgrupadaDTO;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import com.erpmodas.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RelatorioVendaAgrupadaService {
    private final VendaRepository vendaRepository;

    public enum Tipo {
        CLIENTES, PRODUTOS, CATALOGO, ESTACOES, CORES, TAMANHOS, CATEGORIAS
    }

    @Transactional(readOnly = true)
    public List<VendaAgrupadaDTO> gerar(
            Tipo tipo, LocalDate inicio, LocalDate fim
    ) {
        LocalDate dataInicial = inicio != null
                ? inicio : LocalDate.of(1900, 1, 1);
        LocalDate dataFinal = fim != null
                ? fim : LocalDate.of(9999, 12, 31);

        if (dataInicial.isAfter(dataFinal)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data inicial deve ser anterior ou igual à data final."
            );
        }

        List<Venda> vendas = vendaRepository.buscarParaRelatoriosAgrupados(
                dataInicial, dataFinal
        );
        Map<String, Acumulado> grupos = new LinkedHashMap<>();

        for (Venda venda : vendas) {
            if (tipo == Tipo.CLIENTES) {
                adicionar(
                        grupos,
                        venda.getCliente().getId().toString(),
                        venda.getCliente().getNome(),
                        1,
                        venda.getValorTotal()
                );
                continue;
            }

            if (tipo == Tipo.ESTACOES) {
                EstacaoInfo estacao = identificarEstacao(venda.getDataVenda());
                adicionar(
                        grupos,
                        estacao.chave,
                        estacao.nome,
                        1,
                        venda.getValorTotal()
                );
                continue;
            }

            List<ItemVenda> itens = venda.getItensVenda();
            BigDecimal subtotalVenda = itens.stream()
                    .map(ItemVenda::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal restante = venda.getValorTotal();

            for (int indice = 0; indice < itens.size(); indice++) {
                ItemVenda item = itens.get(indice);

                // Distribui o desconto da venda entre os itens. O último
                // recebe a diferença de arredondamento.
                BigDecimal valorLiquido;
                if (indice == itens.size() - 1) {
                    valorLiquido = restante;
                } else if (subtotalVenda.signum() == 0) {
                    valorLiquido = BigDecimal.ZERO;
                } else {
                    valorLiquido = venda.getValorTotal()
                            .multiply(item.getSubTotal())
                            .divide(subtotalVenda, 2, RoundingMode.HALF_UP);
                    restante = restante.subtract(valorLiquido);
                }

                VariacaoProduto variacao = item.getVariacaoProduto();
                Produto produto = variacao.getProduto();

                if (tipo == Tipo.CATALOGO
                        && (!Boolean.TRUE.equals(produto.getNoCatalogo())
                        || !Boolean.TRUE.equals(produto.getAtivo()))) {
                    continue;
                }

                String chave;
                String nome;

                switch (tipo) {
                    case PRODUTOS -> {
                        chave = produto.getId().toString();
                        nome = produto.getNome();
                    }
                    case CORES -> {
                        chave = variacao.getCor().getId().toString();
                        nome = variacao.getCor().getNome();
                    }
                    case TAMANHOS -> {
                        chave = variacao.getTamanho().getId().toString();
                        nome = variacao.getTamanho()
                                .getTamanho().getDescricao();
                    }
                    case CATEGORIAS -> {
                        chave = produto.getCategoria().getId().toString();
                        nome = produto.getCategoria().getNome();
                    }
                    default -> throw new IllegalStateException(
                            "Agrupamento inválido: " + tipo
                    );
                }

                adicionar(
                        grupos, chave, nome,
                        item.getQuantidade(), valorLiquido
                );
            }
        }

        List<VendaAgrupadaDTO> resultado = new ArrayList<>();
        for (Acumulado grupo : grupos.values()) {
            resultado.add(new VendaAgrupadaDTO(
                    grupo.chave,
                    grupo.nome,
                    grupo.quantidade,
                    grupo.faturamento
            ));
        }

        if (tipo == Tipo.ESTACOES) {
            resultado.sort(Comparator.comparing(VendaAgrupadaDTO::getChave));
        } else {
            resultado.sort(
                    Comparator.comparingInt(
                            VendaAgrupadaDTO::getQuantidade
                    ).reversed()
            );
        }
        return resultado;
    }

    private void adicionar(
            Map<String, Acumulado> grupos,
            String chave,
            String nome,
            int quantidade,
            BigDecimal valor
    ) {
        Acumulado grupo = grupos.computeIfAbsent(
                chave, ignorada -> new Acumulado(chave, nome)
        );
        grupo.quantidade += quantidade;
        grupo.faturamento = grupo.faturamento.add(valor);
    }

    private EstacaoInfo identificarEstacao(LocalDate data) {
        int mes = data.getMonthValue();
        int ano = data.getYear();

        if (mes == 12) {
            return new EstacaoInfo(
                    (ano + 1) + "-VERAO",
                    "Verão " + ano + "/" + (ano + 1)
            );
        }
        if (mes <= 2) {
            return new EstacaoInfo(
                    ano + "-VERAO",
                    "Verão " + (ano - 1) + "/" + ano
            );
        }
        if (mes <= 5) {
            return new EstacaoInfo(ano + "-OUTONO", "Outono " + ano);
        }
        if (mes <= 8) {
            return new EstacaoInfo(ano + "-INVERNO", "Inverno " + ano);
        }
        return new EstacaoInfo(
                ano + "-PRIMAVERA", "Primavera " + ano
        );
    }

    private static class Acumulado {
        private final String chave;
        private final String nome;
        private int quantidade;
        private BigDecimal faturamento = BigDecimal.ZERO;

        private Acumulado(String chave, String nome) {
            this.chave = chave;
            this.nome = nome;
        }
    }

    private static class EstacaoInfo {
        private final String chave;
        private final String nome;

        private EstacaoInfo(String chave, String nome) {
            this.chave = chave;
            this.nome = nome;
        }
    }
}
