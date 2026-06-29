package com.erpmodas.controller;

import com.erpmodas.dto.assessoria.AssessoriaDTO;
import com.erpmodas.service.AssessoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessorias")
@RequiredArgsConstructor
public class AssessoriaController {
    private final AssessoriaService assessoriaService;

    @GetMapping
    public ResponseEntity<List<AssessoriaDTO>> listar() {
        List<AssessoriaDTO> lista = assessoriaService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessoriaDTO> buscarPorId(@PathVariable Long id) {
        AssessoriaDTO assessoria = assessoriaService.buscarPorId(id);
        return ResponseEntity.ok(assessoria);
    }

    @PostMapping
    public ResponseEntity<AssessoriaDTO> salvar(@RequestBody AssessoriaDTO dto) {
        AssessoriaDTO salvo = assessoriaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessoriaDTO> atualizar(@PathVariable Long id, @RequestBody AssessoriaDTO dto) {
        AssessoriaDTO atualizado = assessoriaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assessoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}