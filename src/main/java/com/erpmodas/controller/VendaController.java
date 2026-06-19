package com.erpmodas.controller;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
import com.erpmodas.dto.venda.VendaReqDTO;
import com.erpmodas.dto.venda.VendaResponseDTO;
import com.erpmodas.dto.venda.VendaUpdateDTO;
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
    public ResponseEntity<VendaResponseDTO> criar(@Valid @RequestBody VendaReqDTO dto) {
        VendaResponseDTO criacao = vendaService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listar() {
        return ResponseEntity.ok(vendaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemVendaResponseDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.listarItensDaVenda(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VendaUpdateDTO dto) {
        return ResponseEntity.ok(vendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
