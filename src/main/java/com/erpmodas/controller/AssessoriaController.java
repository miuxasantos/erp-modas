package com.erpmodas.controller;

import com.erpmodas.dto.assessoria.AssessoriaDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.AssessoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessorias")
@RequiredArgsConstructor
public class AssessoriaController {
    private final AssessoriaService assessoriaService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<AssessoriaDTO>> listar() {
        List<AssessoriaDTO> lista = assessoriaService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<AssessoriaDTO> buscarPorId(@PathVariable Long id) {
        AssessoriaDTO assessoria = assessoriaService.buscarPorId(id);
        return ResponseEntity.ok(assessoria);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "assessoria")
    public ResponseEntity<AssessoriaDTO> salvar(@RequestBody AssessoriaDTO dto) {
        AssessoriaDTO salvo = assessoriaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "assessoria")
    public ResponseEntity<AssessoriaDTO> atualizar(@PathVariable Long id, @RequestBody AssessoriaDTO dto) {
        AssessoriaDTO atualizado = assessoriaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "assessoria")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assessoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}