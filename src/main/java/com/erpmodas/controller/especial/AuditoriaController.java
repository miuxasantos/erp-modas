package com.erpmodas.controller.especial;

import com.erpmodas.dto.especial.AuditoriaDTO;
import com.erpmodas.service.especial.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaDTO>> listar() {
        List<AuditoriaDTO> lista = auditoriaService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> buscarPorId(@PathVariable Long id) {
        AuditoriaDTO auditoria = auditoriaService.buscarPorId(id);
        return ResponseEntity.ok(auditoria);
    }

    @PostMapping
    public ResponseEntity<AuditoriaDTO> salvar(@RequestBody AuditoriaDTO dto) {
        AuditoriaDTO salvo = auditoriaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> atualizar(@PathVariable Long id, @RequestBody AuditoriaDTO dto) {
        AuditoriaDTO atualizado = auditoriaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        auditoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
