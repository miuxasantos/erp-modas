package com.erpmodas.controller;

import com.erpmodas.dto.sessiontoken.SessionTokenDTO;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.SessionTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessionToken")
@RequiredArgsConstructor
public class SessionTokenController {

    private final SessionTokenService service;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<SessionTokenDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<SessionTokenDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    public ResponseEntity<SessionTokenDTO> salvar(@RequestBody SessionTokenDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
