package com.erpmodas.service.relatorios;

import com.erpmodas.dto.relatorios.financeiro.*;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.OrigemMov;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.enums.TipoMovCaixa;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import com.erpmodas.repository.VendaRepository;
import com.erpmodas.repository.dependentes.ContasPagarRepository;
import com.erpmodas.repository.dependentes.ContasReceberRepository;
import com.erpmodas.repository.dependentes.MovimentacoesCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioFinanceiroService {
    private final VendaRepository vendaRepository;
    private final ContasPagarRepository contasPagarRepository;
    private final ContasReceberRepository contasReceberRepository;
    private final MovimentacoesCaixaRepository movimentacoesRepository;

    @Transactional(readOnly = true)
    public FaturamentoPeriodoDTO faturamento(
            LocalDate inicio, LocalDate fim
    ) {
        validarPeriodo(inicio, fim);
        List<Venda> vendas = vendaRepository
                .findByDataVendaBetween(inicio, fim);

        BigDecimal total = vendas.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FaturamentoPeriodoDTO(
                inicio, fim, vendas.size(), total
        );
    }

    @Transactional(readOnly = true)
    public ContasPeriodoDTO contasAPagar(
            LocalDate inicio, LocalDate fim
    ) {
        validarPeriodo(inicio, fim);
        List<ContasPagar> contas = contasPagarRepository
                .findByDataVencimentoBetween(inicio, fim);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pago = BigDecimal.ZERO;
        int quantidade = 0;

        for (ContasPagar conta : contas) {
            if (conta.getStatusConta() == StatusConta.CANCELADO) {
                continue;
            }
            quantidade++;
            total = total.add(conta.getValor());
            if (conta.getStatusConta() == StatusConta.PAGO) {
                pago = pago.add(conta.getValor());
            }
        }

        return new ContasPeriodoDTO(
                inicio, fim, quantidade, total,
                pago, total.subtract(pago)
        );
    }

    @Transactional(readOnly = true)
    public ContasPeriodoDTO contasAReceber(
            LocalDate inicio, LocalDate fim
    ) {
        validarPeriodo(inicio, fim);
        List<ContasReceber> contas = contasReceberRepository
                .findByDataVencimentoBetween(inicio, fim);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal recebido = BigDecimal.ZERO;
        int quantidade = 0;

        for (ContasReceber conta : contas) {
            if (conta.getStatusConta() == StatusConta.CANCELADO) {
                continue;
            }
            quantidade++;
            total = total.add(conta.getValor());
            if (conta.getStatusConta() == StatusConta.PAGO) {
                recebido = recebido.add(conta.getValor());
            }
        }

        return new ContasPeriodoDTO(
                inicio, fim, quantidade, total,
                recebido, total.subtract(recebido)
        );
    }

    @Transactional(readOnly = true)
    public RelatorioFiscalMensalDTO fiscal(int ano, int mes) {
        YearMonth periodo;
        try {
            periodo = YearMonth.of(ano, mes);
        } catch (DateTimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ano ou mês inválido."
            );
        }

        LocalDate inicio = periodo.atDay(1);
        LocalDate fim = periodo.atEndOfMonth();
        Map<LocalDate, DiaFiscalDTO> dias = new LinkedHashMap<>();
        List<DespesaFiscalDTO> despesas = new ArrayList<>();

        for (LocalDate data = inicio; !data.isAfter(fim);
             data = data.plusDays(1)) {
            DiaFiscalDTO dia = new DiaFiscalDTO();
            dia.setData(data);
            dias.put(data, dia);
        }

        for (ContasReceber conta : contasReceberRepository
                .findByDataRecebimentoBetween(inicio, fim)) {
            if (conta.getStatusConta() == StatusConta.PAGO) {
                adicionarEntrada(
                        dias.get(conta.getDataRecebimento()),
                        conta.getFormaPagamento(),
                        conta.getValor()
                );
            }
        }

        for (ContasPagar conta : contasPagarRepository
                .findByDataPagamentoBetween(inicio, fim)) {
            if (conta.getStatusConta() == StatusConta.PAGO) {
                adicionarSaida(
                        dias.get(conta.getDataPagamento()),
                        conta.getFormaPagamento(),
                        conta.getValor()
                );
                despesas.add(new DespesaFiscalDTO(
                        conta.getDataPagamento(),
                        "Conta a pagar #" + conta.getId(),
                        conta.getFormaPagamento(),
                        conta.getValor()
                ));
            }
        }

        for (MovimentacoesCaixa mov : movimentacoesRepository
                .findByDataBetween(inicio, fim)) {
            // As movimentações originadas de contas já foram somadas acima.
            if (mov.getOrigemMov() != null
                    && mov.getOrigemMov() != OrigemMov.OUTRO) {
                continue;
            }

            DiaFiscalDTO dia = dias.get(mov.getData());
            if (mov.getTipoMovCaixa() == TipoMovCaixa.ENTRADA) {
                adicionarEntrada(
                        dia, mov.getFormaPagamento(),
                        mov.getValor()
                );
            } else if (mov.getTipoMovCaixa() == TipoMovCaixa.SAIDA) {
                adicionarSaida(
                        dia, mov.getFormaPagamento(),
                        mov.getValor()
                );
                despesas.add(new DespesaFiscalDTO(
                        mov.getData(),
                        mov.getDescricao(),
                        mov.getFormaPagamento(),
                        mov.getValor()
                ));
            }
        }

        DiaFiscalDTO totais = new DiaFiscalDTO();
        for (DiaFiscalDTO dia : dias.values()) {
            fecharTotais(dia);
            acumular(totais, dia);
        }
        fecharTotais(totais);

        return new RelatorioFiscalMensalDTO(
                ano, mes,
                new ArrayList<>(dias.values()),
                despesas,
                totais
        );
    }

    private void adicionarEntrada(
            DiaFiscalDTO dia,
            FormaPagamento forma,
            BigDecimal valor
    ) {
        if (forma == FormaPagamento.DINHEIRO) {
            dia.setEntradaDinheiro(
                    dia.getEntradaDinheiro().add(valor)
            );
        } else if (forma == FormaPagamento.PIX) {
            dia.setEntradaPix(dia.getEntradaPix().add(valor));
        } else if (forma == FormaPagamento.CARTAO_CREDITO
                || forma == FormaPagamento.CARTAO_DEBITO) {
            dia.setEntradaCartao(
                    dia.getEntradaCartao().add(valor)
            );
        } else {
            dia.setEntradaOutros(
                    dia.getEntradaOutros().add(valor)
            );
        }
    }

    private void adicionarSaida(
            DiaFiscalDTO dia,
            FormaPagamento forma,
            BigDecimal valor
    ) {
        if (forma == FormaPagamento.DINHEIRO) {
            dia.setSaidaDinheiro(dia.getSaidaDinheiro().add(valor));
        } else if (forma == FormaPagamento.PIX) {
            dia.setSaidaPix(dia.getSaidaPix().add(valor));
        } else if (forma == FormaPagamento.CARTAO_CREDITO
                || forma == FormaPagamento.CARTAO_DEBITO) {
            dia.setSaidaCartao(
                    dia.getSaidaCartao().add(valor)
            );
        } else {
            dia.setSaidaOutros(dia.getSaidaOutros().add(valor));
        }
    }

    private void fecharTotais(DiaFiscalDTO dia) {
        dia.setTotalEntradas(
                dia.getEntradaDinheiro()
                        .add(dia.getEntradaPix())
                        .add(dia.getEntradaCartao())
                        .add(dia.getEntradaOutros())
        );
        dia.setTotalSaidas(
                dia.getSaidaDinheiro()
                        .add(dia.getSaidaPix())
                        .add(dia.getSaidaCartao())
                        .add(dia.getSaidaOutros())
        );
        dia.setSaldo(
                dia.getTotalEntradas().subtract(
                        dia.getTotalSaidas()
                )
        );
    }

    private void acumular(DiaFiscalDTO total, DiaFiscalDTO dia) {
        total.setEntradaDinheiro(total.getEntradaDinheiro()
                .add(dia.getEntradaDinheiro()));
        total.setEntradaPix(total.getEntradaPix()
                .add(dia.getEntradaPix()));
        total.setEntradaCartao(total.getEntradaCartao()
                .add(dia.getEntradaCartao()));
        total.setEntradaOutros(total.getEntradaOutros()
                .add(dia.getEntradaOutros()));

        total.setSaidaDinheiro(total.getSaidaDinheiro()
                .add(dia.getSaidaDinheiro()));
        total.setSaidaPix(total.getSaidaPix()
                .add(dia.getSaidaPix()));
        total.setSaidaCartao(total.getSaidaCartao()
                .add(dia.getSaidaCartao()));
        total.setSaidaOutros(total.getSaidaOutros()
                .add(dia.getSaidaOutros()));
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe um período válido."
            );
        }
    }
}