package com.erpmodas.controller.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.dto.dependentes.contasReceber.DataRecebimentoDTO;
import com.erpmodas.service.dependentes.ContasPagarService;
import com.erpmodas.service.dependentes.ContasReceberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contas-receber")
@RequiredArgsConstructor
public class ContasReceberController {

    private final ContasReceberService contasReceberService;

    @GetMapping
    public ResponseEntity<List<ContasReceberDTO>> listar() {
        List<ContasReceberDTO> lista = contasReceberService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContasReceberDTO> buscarPorId(@PathVariable Long id) {
        ContasReceberDTO contasReceber = contasReceberService.buscarPorId(id);
        return ResponseEntity.ok(contasReceber);
    }

    @GetMapping("/abertas")
    public ResponseEntity<List<ContasReceberDTO>> listarEmAberto() {
        List<ContasReceberDTO> listaAberto = contasReceberService.listarEmAberto();
        return ResponseEntity.ok(listaAberto);
    }

    @GetMapping("/pagas")
    public ResponseEntity<List<ContasReceberDTO>> listarPagas() {
        List<ContasReceberDTO> listaPagas = contasReceberService.listarPagas();
        return ResponseEntity.ok(listaPagas);
    }

    @GetMapping("/vencidas")
    public ResponseEntity<List<ContasReceberDTO>> listarVencidas() {
        List<ContasReceberDTO> listaVencidas = contasReceberService.listarVencidas();
        return ResponseEntity.ok(listaVencidas);
    }

    @PostMapping
    public ResponseEntity<ContasReceberDTO> criar(@RequestBody ContasReceberDTO dto) {
        ContasReceberDTO salvo = contasReceberService.criar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContasReceberDTO> atualizar(@PathVariable Long id, @RequestBody ContasReceberDTO dto) {
        ContasReceberDTO entity = contasReceberService.atualizar(id, dto);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/{id}/receber")
    public ResponseEntity<ContasReceberDTO> receber(@PathVariable Long id, @RequestBody DataRecebimentoDTO dto) {
        ContasReceberDTO entity = contasReceberService.marcarComoPago(id, dto.getDataRecebimento());
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContasReceberDTO> deletar(@PathVariable Long id) {
        contasReceberService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
