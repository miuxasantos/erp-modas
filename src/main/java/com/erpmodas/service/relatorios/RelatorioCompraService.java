package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.compra.CompraResumoDTO;
import com.erpmodas.dto.relatorios.compra.ComprasPorPeriodoDTO;
import com.erpmodas.dto.relatorios.compra.FornecedorMaisComprasDTO;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import com.erpmodas.repository.CompraRepository;
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
public class RelatorioCompraService {
    private final CompraRepository compraRepository;

    @Transactional(readOnly = true)
    public ComprasPorPeriodoDTO porPeriodo(
            LocalDate inicio, LocalDate fim
    ) {
        List<Compra> compras = buscar(inicio, fim);
        List<CompraResumoDTO> linhas = compras.stream()
                .map(this::resumirCompra)
                .toList();

        BigDecimal valorTotal = compras.stream()
                .map(Compra::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ticketMedio = compras.isEmpty()
                ? BigDecimal.ZERO
                : valorTotal.divide(
                BigDecimal.valueOf(compras.size()),
                2,
                RoundingMode.HALF_UP
        );

        return new ComprasPorPeriodoDTO(
                inicio,
                fim,
                compras.size(),
                valorTotal,
                ticketMedio,
                linhas
        );
    }

    @Transactional(readOnly = true)
    public List<FornecedorMaisComprasDTO> fornecedores(
            LocalDate inicio, LocalDate fim
    ) {
        Map<Long, FornecedorMaisComprasDTO> grupos =
                new LinkedHashMap<>();

        for (Compra compra : buscar(inicio, fim)) {
            Long id = compra.getFornecedor().getId();

            FornecedorMaisComprasDTO grupo = grupos.computeIfAbsent(
                    id,
                    ignorado -> new FornecedorMaisComprasDTO(
                            id,
                            compra.getFornecedor().getNome(),
                            0,
                            0,
                            BigDecimal.ZERO
                    )
            );

            int unidades = compra.getItensCompra().stream()
                    .mapToInt(ItemCompra::getQuantidade)
                    .sum();

            grupo.setQuantidadeCompras(
                    grupo.getQuantidadeCompras() + 1
            );
            grupo.setUnidadesAdicionadas(
                    grupo.getUnidadesAdicionadas() + unidades
            );
            grupo.setValorTotal(
                    grupo.getValorTotal().add(compra.getValorTotal())
            );
        }

        List<FornecedorMaisComprasDTO> resultado =
                new ArrayList<>(grupos.values());
        resultado.sort(
                Comparator.comparingInt(
                        FornecedorMaisComprasDTO::getQuantidadeCompras
                ).reversed()
        );
        return resultado;
    }

    @Transactional(readOnly = true)
    public List<CompraResumoDTO> comMaisProdutos(
            LocalDate inicio, LocalDate fim
    ) {
        List<CompraResumoDTO> resultado = buscar(inicio, fim)
                .stream()
                .map(this::resumirCompra)
                .collect(java.util.stream.Collectors.toCollection(
                        ArrayList::new
                ));

        resultado.sort(
                Comparator.comparingInt(
                        CompraResumoDTO::getUnidadesAdicionadas
                ).reversed()
        );
        return resultado;
    }

    private List<Compra> buscar(LocalDate inicio, LocalDate fim) {
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

        return compraRepository.buscarParaRelatorio(
                dataInicial, dataFinal
        );
    }

    private CompraResumoDTO resumirCompra(Compra compra) {
        int unidades = compra.getItensCompra().stream()
                .mapToInt(ItemCompra::getQuantidade)
                .sum();

        int produtosDistintos = (int) compra.getItensCompra()
                .stream()
                .map(item -> item.getVariacaoProduto()
                        .getProduto().getId())
                .distinct()
                .count();

        return new CompraResumoDTO(
                compra.getId(),
                compra.getLote(),
                compra.getDataChegada(),
                compra.getFornecedor().getNome(),
                unidades,
                produtosDistintos,
                compra.getValorTotal()
        );
    }

}
