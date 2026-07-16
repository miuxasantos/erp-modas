package com.erpmodas.controller;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
import com.erpmodas.dto.venda.VendaReqDTO;
import com.erpmodas.dto.venda.VendaResponseDTO;
import com.erpmodas.dto.venda.VendaUpdateDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "venda")
    public ResponseEntity<VendaResponseDTO> criar(@Valid @RequestBody VendaReqDTO dto) {
        VendaResponseDTO criacao = vendaService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listar() {
        return ResponseEntity.ok(vendaService.listar());
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemVendaResponseDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.listarItensDaVenda(id));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "venda")
    public ResponseEntity<VendaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VendaUpdateDTO dto) {
        return ResponseEntity.ok(vendaService.atualizar(id, dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "venda")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
