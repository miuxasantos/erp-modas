package com.erpmodas.controller;

import com.erpmodas.dto.caixa.CaixaReqDTO;
import com.erpmodas.dto.caixa.CaixaResponseDTO;
import com.erpmodas.dto.caixa.CaixaUpdateDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.mapper.CaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.service.CaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixas")
@RequiredArgsConstructor
public class CaixaController {

    private final CaixaService service;
    private final CaixaMapper caixaMapper;

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<CaixaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CaixaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/atual")
    public ResponseEntity<CaixaResponseDTO> atual() {
        return ResponseEntity.ok(service.buscarCaixaAtual());
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "caixa")
    public ResponseEntity<CaixaResponseDTO> salvar(@RequestBody CaixaReqDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "caixa")
    public ResponseEntity<CaixaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CaixaUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "caixa")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping("/abrir")
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "caixa")
    public ResponseEntity<CaixaResponseDTO>  abrir() {
        Caixa caixa = service.buscarOuCriarCaixaDoDia();
        CaixaResponseDTO dto = caixaMapper.toDTO(caixa);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("{id}/fechar")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "caixa")
    public ResponseEntity<CaixaResponseDTO> fechar(@PathVariable Long id) {
        return ResponseEntity.ok(service.fecharCaixaDoDia(id));
    }
}
