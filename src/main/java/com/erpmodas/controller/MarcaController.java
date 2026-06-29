package com.erpmodas.controller;

import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> listar() {
        List<MarcaDTO> lista = marcaService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(@PathVariable Long id) {
        MarcaDTO marca = marcaService.buscarPorId(id);
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> salvar(@RequestBody MarcaDTO dto) {
        MarcaDTO salvo = marcaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> atualizar(@PathVariable Long id, @RequestBody MarcaDTO dto) {
        MarcaDTO atualizado = marcaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        marcaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
