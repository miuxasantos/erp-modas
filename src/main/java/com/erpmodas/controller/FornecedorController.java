package com.erpmodas.controller;

import com.erpmodas.dto.fornecedor.FornecedorReqDTO;
import com.erpmodas.dto.fornecedor.FornecedorResponseDTO;
import com.erpmodas.dto.fornecedor.FornecedorUpdateDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        List<FornecedorResponseDTO> lista = fornecedorService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        FornecedorResponseDTO fornecedor = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(fornecedor);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "fornecedor")
    public ResponseEntity<FornecedorResponseDTO> salvar(@RequestBody FornecedorReqDTO dto) {
        FornecedorResponseDTO salvo = fornecedorService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "fornecedor")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Long id, @RequestBody FornecedorUpdateDTO dto) {
        FornecedorResponseDTO atualizado = fornecedorService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "fornecedor")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
