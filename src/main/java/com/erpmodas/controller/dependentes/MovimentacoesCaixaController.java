package com.erpmodas.controller.dependentes;

import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.service.dependentes.MovimentacoesCaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes-caixa")
@RequiredArgsConstructor
public class MovimentacoesCaixaController {

    private final MovimentacoesCaixaService movimentacoesCaixaService;

    @PostMapping("/caixa/{caixaId}")
    public ResponseEntity<MovimentacoesCaixaDTO> criar(
            @PathVariable Long caixaId,
            @RequestBody MovimentacoesCaixaDTO dto) {

        return ResponseEntity.status(201).body(movimentacoesCaixaService.criar(caixaId, dto));
    }

    @GetMapping("/caixa/{caixaId}")
    public ResponseEntity<List<MovimentacoesCaixaDTO>> listar(@PathVariable Long caixaId) {

        return ResponseEntity.ok(movimentacoesCaixaService.listarPorCaixa(caixaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        movimentacoesCaixaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
