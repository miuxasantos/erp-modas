package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.TecidoDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.apoio.TecidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecidos")
@RequiredArgsConstructor
public class TecidoController {

    private final TecidoService tecidoService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<TecidoDTO>> listar() {
        List<TecidoDTO> lista = tecidoService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<TecidoDTO> buscarPorId(@PathVariable Long id) {
        TecidoDTO tecido = tecidoService.buscarPorId(id);
        return ResponseEntity.ok(tecido);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "tecido")
    public ResponseEntity<TecidoDTO> salvar(@RequestBody TecidoDTO dto) {
        TecidoDTO salvo = tecidoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "tecido")
    public ResponseEntity<TecidoDTO> atualizar(@PathVariable Long id, @RequestBody TecidoDTO dto) {
        TecidoDTO atualizado = tecidoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "tecido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
