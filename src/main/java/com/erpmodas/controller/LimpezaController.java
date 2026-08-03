package com.erpmodas.controller;

import com.erpmodas.dto.limpeza.LimpezaResultDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.LimpezaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manutencao")
@RequiredArgsConstructor
public class LimpezaController {

    private final LimpezaService limpezaService;

    @PostMapping("/limpar-orfaos")
    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "Arquivo")
    public ResponseEntity<LimpezaResultDTO> limpar(@RequestParam(defaultValue = "true") boolean simulacao) {
        return ResponseEntity.ok(limpezaService.limparOrfaos(simulacao));
    }
}
