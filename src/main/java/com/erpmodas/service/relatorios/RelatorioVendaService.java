package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.venda.RelatoriosVendaDTO;
import com.erpmodas.dto.relatorios.venda.VendaPorDiaDTO;
import com.erpmodas.dto.relatorios.venda.VendaRelatorioItemDTO;
import com.erpmodas.dto.venda.VendaResponseDTO;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class RelatorioVendaService {
    private final VendaRepository vendaRepository;

    @Transactional(readOnly = true)
    public RelatoriosVendaDTO gerar(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data inicial deve ser anterior ou igual à data final."
            );
        }

        List<Venda> vendas = vendaRepository.buscarParaRelatorio(inicio, fim);
        List<VendaRelatorioItemDTO> linhas = new ArrayList<>();
        Map<LocalDate, AcumuladoDia> dias = new TreeMap<>();
        BigDecimal faturamento = BigDecimal.ZERO;

        for (Venda venda : vendas) {
            BigDecimal valor = venda.getValorTotal();

            linhas.add(new VendaRelatorioItemDTO(
                    venda.getId(),
                    venda.getDataVenda(),
                    venda.getCliente().getNome(),
                    venda.getFormaPagamento(),
                    valor
            ));

            faturamento = faturamento.add(valor);
            dias.computeIfAbsent(
                    venda.getDataVenda(),
                    data -> new AcumuladoDia()
            ).adicionar(valor);
        }

        List<VendaPorDiaDTO> serie = dias.entrySet().stream()
                .map(dia -> new VendaPorDiaDTO(
                        dia.getKey(),
                        dia.getValue().quantidade,
                        dia.getValue().total
                ))
                .toList();

        BigDecimal ticketMedio = vendas.isEmpty()
                ? BigDecimal.ZERO
                : faturamento.divide(
                BigDecimal.valueOf(vendas.size()),
                2,
                RoundingMode.HALF_UP
        );

        return new RelatoriosVendaDTO(
                inicio,
                fim,
                vendas.size(),
                faturamento,
                ticketMedio,
                serie,
                linhas
        );
    }

    private static class AcumuladoDia {
        private int quantidade;
        private BigDecimal total = BigDecimal.ZERO;

        void adicionar(BigDecimal valor) {
            quantidade++;
            total = total.add(valor);
        }
    }
}
