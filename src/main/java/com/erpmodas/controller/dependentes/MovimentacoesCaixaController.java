package com.erpmodas.controller.dependentes;

import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.dependentes.MovimentacoesCaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes-caixa")
@RequiredArgsConstructor
public class MovimentacoesCaixaController {

    private final MovimentacoesCaixaService movimentacoesCaixaService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping("/caixa/{caixaId}")
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "movimentacoes_caixa")
    public ResponseEntity<MovimentacoesCaixaDTO> criar(
            @PathVariable Long caixaId,
            @RequestBody MovimentacoesCaixaDTO dto) {

        return ResponseEntity.status(201).body(movimentacoesCaixaService.criar(caixaId, dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/caixa/{caixaId}")
    public ResponseEntity<List<MovimentacoesCaixaDTO>> listar(@PathVariable Long caixaId) {

        return ResponseEntity.ok(movimentacoesCaixaService.listarPorCaixa(caixaId));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "movimentacoes_caixa")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        movimentacoesCaixaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
