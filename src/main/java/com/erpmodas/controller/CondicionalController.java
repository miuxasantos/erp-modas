package com.erpmodas.controller;

import com.erpmodas.dto.condicional.CondicionalReqDTO;
import com.erpmodas.dto.condicional.CondicionalResponseDTO;
import com.erpmodas.dto.condicional.CondicionalUpdateDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.CondicionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/condicionais")
@RequiredArgsConstructor
public class CondicionalController {

    private final CondicionalService condicionalService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "condicional")
    public ResponseEntity<CondicionalResponseDTO> criar(@Valid @RequestBody CondicionalReqDTO dto) {
        CondicionalResponseDTO criacao = condicionalService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<CondicionalResponseDTO>> listar() {
        return ResponseEntity.ok(condicionalService.listar());
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<CondicionalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(condicionalService.buscarPorId(id));
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemCondicionalResponseDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(condicionalService.listarItensDaCondicional(id));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "condicional")
    public ResponseEntity<CondicionalResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CondicionalUpdateDTO dto) {
        return ResponseEntity.ok(condicionalService.atualizar(id, dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "condicional")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        condicionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
