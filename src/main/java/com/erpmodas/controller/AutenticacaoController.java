package com.erpmodas.controller;

import com.erpmodas.dto.autenticacao.LoginRequestDTO;
import com.erpmodas.dto.autenticacao.LoginResponseDTO;
import com.erpmodas.service.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            service.logout(authHeader.substring(7));
        }
        return ResponseEntity.noContent().build();
    }
}
