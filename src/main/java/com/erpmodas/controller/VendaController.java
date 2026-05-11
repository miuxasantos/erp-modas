package com.erpmodas.controller;

import com.erpmodas.dto.venda.VendaDTO;
import com.erpmodas.dto.dependentes.itemVenda.ItemVendaDTO;
import com.erpmodas.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaDTO> criar(@Valid @RequestBody VendaDTO dto) {
        VendaDTO criacao = vendaService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> listar() {
        return ResponseEntity.ok(vendaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemVendaDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.listarItensDaVenda(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VendaDTO dto) {
        return ResponseEntity.ok(vendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
