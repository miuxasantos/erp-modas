package com.erpmodas.controller.apoio;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoReqDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoUpdateDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos/{produtoId}/variacoes")
@RequiredArgsConstructor
public class VariacaoProdutoController {

    private final VariacaoProdutoService variacaoProdutoService;

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<VariacaoProdutoResponseDTO>> listar(@PathVariable Long produtoId) {
        List<VariacaoProdutoResponseDTO> lista = variacaoProdutoService.listarPorProduto(produtoId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<VariacaoProdutoResponseDTO> buscarPorId(@PathVariable Long produtoId, @PathVariable Long id) {
        VariacaoProdutoResponseDTO variacaoProduto = variacaoProdutoService.buscarPorId(id);
        return ResponseEntity.ok(variacaoProduto);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "variacao_produto")
    public ResponseEntity<VariacaoProdutoResponseDTO> salvar(@PathVariable Long produtoId, @RequestBody VariacaoProdutoReqDTO dto) {
        dto.setProdutoId(produtoId);
        VariacaoProdutoResponseDTO salvo = variacaoProdutoService.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "variacao_produto")
    public ResponseEntity<VariacaoProdutoResponseDTO> atualizar(@PathVariable Long produtoId, @PathVariable Long id, @RequestBody VariacaoProdutoUpdateDTO dto) {
        VariacaoProdutoResponseDTO atualizado = variacaoProdutoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "variacao_produto")
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId, @PathVariable Long id) {
        variacaoProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
