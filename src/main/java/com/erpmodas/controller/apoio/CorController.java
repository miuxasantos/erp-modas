package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.apoio.CorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cores")
@RequiredArgsConstructor
public class CorController {

    private final CorService corService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<CorDTO>> listar() {
        List<CorDTO> lista = corService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<CorDTO> buscarPorId(@PathVariable Long id) {
        CorDTO cor = corService.buscarPorId(id);
        return ResponseEntity.ok(cor);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "cor")
    public ResponseEntity<CorDTO> salvar(@RequestBody CorDTO dto) {
        CorDTO salvo = corService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "cor")
    public ResponseEntity<CorDTO> atualizar(@PathVariable Long id, @RequestBody CorDTO dto) {
        CorDTO atualizado = corService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "cor")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        corService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
