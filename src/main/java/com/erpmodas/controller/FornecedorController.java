package com.erpmodas.controller;

import com.erpmodas.dto.fornecedor.FornecedorReqDTO;
import com.erpmodas.dto.fornecedor.FornecedorResponseDTO;
import com.erpmodas.dto.fornecedor.FornecedorUpdateDTO;
import com.erpmodas.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        List<FornecedorResponseDTO> lista = fornecedorService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        FornecedorResponseDTO fornecedor = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(fornecedor);
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> salvar(@RequestBody FornecedorReqDTO dto) {
        FornecedorResponseDTO salvo = fornecedorService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Long id, @RequestBody FornecedorUpdateDTO dto) {
        FornecedorResponseDTO atualizado = fornecedorService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
