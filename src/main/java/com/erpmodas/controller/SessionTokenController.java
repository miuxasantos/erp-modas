package com.erpmodas.controller;

import com.erpmodas.dto.sessiontoken.SessionTokenDTO;
import com.erpmodas.service.SessionTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessionToken")
@RequiredArgsConstructor
public class SessionTokenController {

    private final SessionTokenService service;

    @GetMapping
    public ResponseEntity<List<SessionTokenDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionTokenDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SessionTokenDTO> salvar(@RequestBody SessionTokenDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionTokenDTO> atualizar(@PathVariable Long id, @RequestBody SessionTokenDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
