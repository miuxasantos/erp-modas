package com.erpmodas.controller.relatorios;

import com.erpmodas.dto.relatorios.venda.RelatoriosVendaDTO;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.relatorios.RelatorioVendaAgrupadaService;
import com.erpmodas.service.relatorios.RelatorioVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erpmodas.dto.relatorios.venda.VendaAgrupadaDTO;
import com.erpmodas.service.relatorios.RelatorioVendaAgrupadaService.Tipo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios/vendas")
@RequiredArgsConstructor
@PreAuthorize(RoleAuthority.PROPRIETARIO)
public class RelatoriosVendaController {
    private final RelatorioVendaService relatorioVendaService;
    private final RelatorioVendaAgrupadaService relatorioVendaAgrupadaService;

    @GetMapping
    public RelatoriosVendaDTO consultar(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        return relatorioVendaService.gerar(inicio, fim);
    }

    @GetMapping("/{agrupamento}")
    public List<VendaAgrupadaDTO> consultarAgrupado(
            @PathVariable String agrupamento,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim
    ) {
        Tipo tipo;
        try {
            tipo = Tipo.valueOf(agrupamento.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Agrupamento de vendas inválido."
            );
        }

        return relatorioVendaAgrupadaService.gerar(tipo, inicio, fim);
    }
}
