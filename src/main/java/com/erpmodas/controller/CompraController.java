package com.erpmodas.controller;

import com.erpmodas.dto.compra.CompraDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraDTO;
import com.erpmodas.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraDTO> criar(@Valid @RequestBody CompraDTO dto) {
        CompraDTO criacao = compraService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @GetMapping
    public ResponseEntity<List<CompraDTO>> listar() {
        return ResponseEntity.ok(compraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemCompraDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.listarItensDaCompra(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraDTO dto) {
        return ResponseEntity.ok(compraService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
