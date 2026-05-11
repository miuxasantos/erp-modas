package com.erpmodas.controller;

import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> lista = categoriaService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody CategoriaDTO dto) {
        CategoriaDTO salvo = categoriaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        CategoriaDTO atualizado = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
