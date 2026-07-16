package com.erpmodas.controller.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.contasPagar.DataPagamentoDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.dependentes.ContasPagarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contas-pagar")
@RequiredArgsConstructor
public class ContasPagarController {

    private final ContasPagarService contasPagarService;

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping
    public ResponseEntity<List<ContasPagarDTO>> listar() {
        List<ContasPagarDTO> lista = contasPagarService.listar();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/{id}")
    public ResponseEntity<ContasPagarDTO> buscarPorId(@PathVariable Long id) {
        ContasPagarDTO contasPagar = contasPagarService.buscarPorId(id);
        return ResponseEntity.ok(contasPagar);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/abertas")
    public ResponseEntity<List<ContasPagarDTO>> listarEmAberto() {
        List<ContasPagarDTO> listaAberto = contasPagarService.listarEmAberto();
        return ResponseEntity.ok(listaAberto);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/pagas")
    public ResponseEntity<List<ContasPagarDTO>> listarPagas() {
        List<ContasPagarDTO> listaPagas = contasPagarService.listarPagas();
        return ResponseEntity.ok(listaPagas);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @GetMapping("/vencidas")
    public ResponseEntity<List<ContasPagarDTO>> listarVencidas() {
        List<ContasPagarDTO> listaVencidas = contasPagarService.listarVencidas();
        return ResponseEntity.ok(listaVencidas);
    }

    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    @PostMapping
    @Auditar(acao = TipoAcaoAud.CREATE, entidade = "contas_pagar")
    public ResponseEntity<ContasPagarDTO> criar(@RequestBody ContasPagarDTO dto) {
        ContasPagarDTO salvo = contasPagarService.criar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "contas_pagar")
    public ResponseEntity<ContasPagarDTO> atualizar(@PathVariable Long id, @RequestBody ContasPagarDTO dto) {
        ContasPagarDTO entity = contasPagarService.atualizar(id, dto);
        return ResponseEntity.ok(entity);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @PutMapping("/{id}/pagar")
    @Auditar(acao = TipoAcaoAud.UPDATE, entidade = "contas_pagar")
    public ResponseEntity<ContasPagarDTO> pagar(@PathVariable Long id, @RequestBody DataPagamentoDTO dto) {
        ContasPagarDTO entity = contasPagarService.marcarComoPago(id, dto.getDataPagamento());
        return ResponseEntity.ok(entity);
    }

    @PreAuthorize(RoleAuthority.PROPRIETARIO)
    @DeleteMapping("/{id}")
    @Auditar(acao = TipoAcaoAud.DELETE, entidade = "contas_pagar")
    public ResponseEntity<ContasPagarDTO> deletar(@PathVariable Long id) {
        contasPagarService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
