package com.erpmodas.controller.apoio;

import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.apoio.TamanhoService;
import com.erpmodas.dto.apoio.TamanhoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tamanhos")
@RequiredArgsConstructor
public class TamanhoController {

    private final TamanhoService tamanhoService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<TamanhoDTO>> listar() {
        List<TamanhoDTO> lista = tamanhoService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<TamanhoDTO> buscarPorId(@PathVariable Long id) {
        TamanhoDTO tamanho = tamanhoService.buscarPorId(id);
        return ResponseEntity.ok(tamanho);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "tamanho")
    public ResponseEntity<TamanhoDTO> salvar(@RequestBody TamanhoDTO dto) {
        TamanhoDTO salvo = tamanhoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "tamanho")
    public ResponseEntity<TamanhoDTO> atualizar(@PathVariable Long id, @RequestBody TamanhoDTO dto) {
        TamanhoDTO atualizado = tamanhoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "tamanho")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tamanhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
