package com.erpmodas.controller;

import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final MarcaService marcaService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<MarcaDTO>> listar() {
        List<MarcaDTO> lista = marcaService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(@PathVariable Long id) {
        MarcaDTO marca = marcaService.buscarPorId(id);
        return ResponseEntity.ok(marca);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "marca")
    public ResponseEntity<MarcaDTO> salvar(@RequestBody MarcaDTO dto) {
        MarcaDTO salvo = marcaService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "marca")
    public ResponseEntity<MarcaDTO> atualizar(@PathVariable Long id, @RequestBody MarcaDTO dto) {
        MarcaDTO atualizado = marcaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "marca")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        marcaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
