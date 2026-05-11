package com.erpmodas.controller;

import com.erpmodas.dto.caixa.CaixaDTO;
import com.erpmodas.service.CaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixas")
@RequiredArgsConstructor
public class CaixaController {

    private final CaixaService service;

    @GetMapping
    public ResponseEntity<List<CaixaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CaixaDTO> salvar(@RequestBody CaixaDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaixaDTO> atualizar(@PathVariable Long id, @RequestBody CaixaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
