package com.erpmodas.controller;

import com.erpmodas.dto.cliente.ClienteReqDTO;
import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.dto.cliente.ClienteUpdateDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        List<ClienteResponseDTO> lista = clienteService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "cliente")
    public ResponseEntity<ClienteResponseDTO> salvar(@RequestBody ClienteReqDTO dto) {
        ClienteResponseDTO salvo = clienteService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "cliente")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO atualizado = clienteService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE , entidade = "cliente")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
