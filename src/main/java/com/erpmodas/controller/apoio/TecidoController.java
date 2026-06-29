package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.TecidoDTO;
import com.erpmodas.service.apoio.TecidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecidos")
@RequiredArgsConstructor
public class TecidoController {

    private final TecidoService tecidoService;

    @GetMapping
    public ResponseEntity<List<TecidoDTO>> listar() {
        List<TecidoDTO> lista = tecidoService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecidoDTO> buscarPorId(@PathVariable Long id) {
        TecidoDTO tecido = tecidoService.buscarPorId(id);
        return ResponseEntity.ok(tecido);
    }

    @PostMapping
    public ResponseEntity<TecidoDTO> salvar(@RequestBody TecidoDTO dto) {
        TecidoDTO salvo = tecidoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecidoDTO> atualizar(@PathVariable Long id, @RequestBody TecidoDTO dto) {
        TecidoDTO atualizado = tecidoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
