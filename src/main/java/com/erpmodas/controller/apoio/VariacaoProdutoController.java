package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variacoes")
@RequiredArgsConstructor
public class VariacaoProdutoController {

    private final VariacaoProdutoService variacaoProdutoService;

    @GetMapping
    public ResponseEntity<List<VariacaoProdutoDTO>> listar() {
        List<VariacaoProdutoDTO> lista = variacaoProdutoService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariacaoProdutoDTO> buscarPorId(@PathVariable Long id) {
        VariacaoProdutoDTO variacaoProduto = variacaoProdutoService.buscarPorId(id);
        return ResponseEntity.ok(variacaoProduto);
    }

    @PostMapping
    public ResponseEntity<VariacaoProdutoDTO> salvar(@RequestBody VariacaoProdutoDTO dto) {
        VariacaoProdutoDTO salvo = variacaoProdutoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariacaoProdutoDTO> atualizar(@PathVariable Long id, @RequestBody VariacaoProdutoDTO dto) {
        VariacaoProdutoDTO atualizado = variacaoProdutoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        variacaoProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
