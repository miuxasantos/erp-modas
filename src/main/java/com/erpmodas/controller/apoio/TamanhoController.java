package com.erpmodas.controller.apoio;

import com.erpmodas.service.apoio.TamanhoService;
import com.erpmodas.dto.apoio.TamanhoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tamanhos")
@RequiredArgsConstructor
public class TamanhoController {

    private final TamanhoService tamanhoService;

    @GetMapping
    public ResponseEntity<List<TamanhoDTO>> listar() {
        List<TamanhoDTO> lista = tamanhoService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TamanhoDTO> buscarPorId(@PathVariable Long id) {
        TamanhoDTO tamanho = tamanhoService.buscarPorId(id);
        return ResponseEntity.ok(tamanho);
    }

    @PostMapping
    public ResponseEntity<TamanhoDTO> salvar(@RequestBody TamanhoDTO dto) {
        TamanhoDTO salvo = tamanhoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TamanhoDTO> atualizar(@PathVariable Long id, @RequestBody TamanhoDTO dto) {
        TamanhoDTO atualizado = tamanhoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tamanhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
