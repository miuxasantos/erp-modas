package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.service.apoio.CorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cores")
@RequiredArgsConstructor
public class CorController {

    private final CorService corService;

    @GetMapping
    public ResponseEntity<List<CorDTO>> listar() {
        List<CorDTO> lista = corService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorDTO> buscarPorId(@PathVariable Long id) {
        CorDTO cor = corService.buscarPorId(id);
        return ResponseEntity.ok(cor);
    }

    @PostMapping
    public ResponseEntity<CorDTO> salvar(@RequestBody CorDTO dto) {
        CorDTO salvo = corService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorDTO> atualizar(@PathVariable Long id, @RequestBody CorDTO dto) {
        CorDTO atualizado = corService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        corService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
