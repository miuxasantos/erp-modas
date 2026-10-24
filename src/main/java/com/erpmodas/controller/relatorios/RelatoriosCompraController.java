package com.erpmodas.controller.relatorios;

import com.erpmodas.dto.relatorios.compra.CompraResumoDTO;
import com.erpmodas.dto.relatorios.compra.ComprasPorPeriodoDTO;
import com.erpmodas.dto.relatorios.compra.FornecedorMaisComprasDTO;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.relatorios.RelatorioCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorios/compras")
@RequiredArgsConstructor
@PreAuthorize(RoleAuthority.PROPRIETARIO)
public class RelatoriosCompraController {
        private final RelatorioCompraService relatorioCompraService;

        @GetMapping("/periodo")
        public ComprasPorPeriodoDTO porPeriodo(
                @RequestParam
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate inicio,

                @RequestParam
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate fim
        ) {
            return relatorioCompraService.porPeriodo(inicio, fim);
        }

        @GetMapping("/fornecedores")
        public List<FornecedorMaisComprasDTO> fornecedores(
                @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate inicio,

                @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate fim
        ) {
            return relatorioCompraService.fornecedores(inicio, fim);
        }

        @GetMapping("/mais-produtos")
        public List<CompraResumoDTO> comMaisProdutos(
                @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate inicio,

                @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate fim
        ) {
            return relatorioCompraService.comMaisProdutos(inicio, fim);
        }
}
