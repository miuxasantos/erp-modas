package com.erpmodas.controller;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalDTO;
import com.erpmodas.dto.condicional.CondicionalDTO;
import com.erpmodas.service.CondicionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/condicionais")
@RequiredArgsConstructor
public class CondicionalController {

    private final CondicionalService condicionalService;

    @PostMapping
    public ResponseEntity<CondicionalDTO> criar(@Valid @RequestBody CondicionalDTO dto) {
        CondicionalDTO criacao = condicionalService.salvar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criacao.getId())
                .toUri();

        return ResponseEntity.created(location).body(criacao);
    }

    @GetMapping
    public ResponseEntity<List<CondicionalDTO>> listar() {
        return ResponseEntity.ok(condicionalService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondicionalDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(condicionalService.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemCondicionalDTO>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(condicionalService.listarItensDaCondicional(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondicionalDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CondicionalDTO dto) {
        return ResponseEntity.ok(condicionalService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        condicionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
