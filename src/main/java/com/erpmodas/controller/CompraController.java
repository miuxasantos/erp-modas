package com.erpmodas.controller;

import com.erpmodas.dto.compra.CompraReqDTO;
import com.erpmodas.dto.compra.CompraResponseDTO;
import com.erpmodas.dto.compra.CompraUpdateDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraResponseDTO;
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
    public ResponseEntity<CompraResponseDTO> criar(@Valid @RequestBody CompraReqDTO dto) {
        CompraResponseDTO criacao = compraService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listar() {
        return ResponseEntity.ok(compraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemCompraResponseDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.listarItensDaCompra(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraUpdateDTO dto) {
        return ResponseEntity.ok(compraService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
