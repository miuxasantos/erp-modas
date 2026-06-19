package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoReqDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoUpdateDTO;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos/{produtoId}/variacoes")
@RequiredArgsConstructor
public class VariacaoProdutoController {

    private final VariacaoProdutoService variacaoProdutoService;

    @GetMapping
    public ResponseEntity<List<VariacaoProdutoResponseDTO>> listar(@PathVariable Long produtoId) {
        List<VariacaoProdutoResponseDTO> lista = variacaoProdutoService.listarPorProduto(produtoId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariacaoProdutoResponseDTO> buscarPorId(@PathVariable Long produtoId, @PathVariable Long id) {
        VariacaoProdutoResponseDTO variacaoProduto = variacaoProdutoService.buscarPorId(id);
        return ResponseEntity.ok(variacaoProduto);
    }

    @PostMapping
    public ResponseEntity<VariacaoProdutoResponseDTO> salvar(@PathVariable Long produtoId, @RequestBody VariacaoProdutoReqDTO dto) {
        dto.setProdutoId(produtoId);
        VariacaoProdutoResponseDTO salvo = variacaoProdutoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariacaoProdutoResponseDTO> atualizar(@PathVariable Long produtoId, @PathVariable Long id, @RequestBody VariacaoProdutoUpdateDTO dto) {
        VariacaoProdutoResponseDTO atualizado = variacaoProdutoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId, @PathVariable Long id) {
        variacaoProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
