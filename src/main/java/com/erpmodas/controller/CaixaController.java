package com.erpmodas.controller;

import com.erpmodas.dto.caixa.CaixaReqDTO;
import com.erpmodas.dto.caixa.CaixaResponseDTO;
import com.erpmodas.dto.caixa.CaixaUpdateDTO;
import com.erpmodas.mapper.CaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.service.CaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixas")
@RequiredArgsConstructor
public class CaixaController {

    private final CaixaService service;
    private final CaixaMapper caixaMapper;

    @GetMapping
    public ResponseEntity<List<CaixaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CaixaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/atual")
    public ResponseEntity<CaixaResponseDTO> atual() {
        return ResponseEntity.ok(service.buscarCaixaAtual());
    }

    @PostMapping
    public ResponseEntity<CaixaResponseDTO> salvar(@RequestBody CaixaReqDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaixaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CaixaUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/abrir")
    public ResponseEntity<CaixaResponseDTO> abrir() {
        Caixa caixa = service.buscarOuCriarCaixaDoDia();
        CaixaResponseDTO dto = caixaMapper.toDTO(caixa);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}/fechar")
    public ResponseEntity<CaixaResponseDTO> fechar(@PathVariable Long id) {
        return ResponseEntity.ok(service.fecharCaixaDoDia(id));
    }


}
