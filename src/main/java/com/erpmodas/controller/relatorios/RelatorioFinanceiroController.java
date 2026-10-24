package com.erpmodas.controller.relatorios;

import com.erpmodas.dto.relatorios.financeiro.*;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.relatorios.RelatorioFinanceiroExportacaoService;
import com.erpmodas.service.relatorios.RelatorioFinanceiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios/financeiro")
@RequiredArgsConstructor
@PreAuthorize(RoleAuthority.PROPRIETARIO)
public class RelatorioFinanceiroController {
    private final RelatorioFinanceiroService service;
    private final RelatorioFinanceiroExportacaoService exportacaoService;

    @GetMapping("/faturamento")
    public FaturamentoPeriodoDTO faturamento(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return service.faturamento(inicio, fim);
    }

    @GetMapping("/contas-pagar")
    public ContasPeriodoDTO contasAPagar(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return service.contasAPagar(inicio, fim);
    }

    @GetMapping("/contas-receber")
    public ContasPeriodoDTO contasAReceber(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return service.contasAReceber(inicio, fim);
    }

    @GetMapping("/fiscal")
    public RelatorioFiscalMensalDTO fiscal(
            @RequestParam int ano,
            @RequestParam int mes
    ) {
        return service.fiscal(ano, mes);
    }

    @GetMapping("/resumo/exportar")
    public ResponseEntity<byte[]> exportarResumo(
            @RequestParam FormatoExportacao formato,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return RespostaArquivo.de(exportacaoService.exportarResumo(formato, inicio, fim));
    }

    @GetMapping("/fiscal/exportar")
    public ResponseEntity<byte[]> exportarFiscal(
            @RequestParam FormatoExportacao formato,
            @RequestParam int ano,
            @RequestParam int mes
    ) {
        return RespostaArquivo.de(exportacaoService.exportarFiscal(formato, ano, mes));
    }
}