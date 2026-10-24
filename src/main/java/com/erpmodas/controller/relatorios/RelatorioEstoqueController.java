package com.erpmodas.controller.relatorios;

import com.erpmodas.dto.relatorios.estoque.EstoqueGeralDTO;
import com.erpmodas.dto.relatorios.estoque.EstoqueProdutoDTO;
import com.erpmodas.enums.FormatoExportacao;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.relatorios.RelatorioEstoqueExportacaoService;
import com.erpmodas.service.relatorios.RelatorioEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios/estoque")
@RequiredArgsConstructor
@PreAuthorize(RoleAuthority.PROPRIETARIO)
public class RelatorioEstoqueController {
    private final RelatorioEstoqueService relatorioEstoqueService;
    private final RelatorioEstoqueExportacaoService relatorioEstoqueExportacaoService;

    @GetMapping("/geral")
    public EstoqueGeralDTO geral(@RequestParam(defaultValue = "false") boolean apenasAtivos) {
        return relatorioEstoqueService.geral(apenasAtivos);
    }

    @GetMapping("/produtos")
    public List<EstoqueProdutoDTO> produtos(@RequestParam(defaultValue = "false") boolean apenasAtivos) {
        return relatorioEstoqueService.produtos(apenasAtivos);
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar(
            @RequestParam FormatoExportacao formato,
            @RequestParam(defaultValue = "false") boolean apenasAtivos
    ) {
        return RespostaArquivo.de(relatorioEstoqueExportacaoService.exportar(formato, apenasAtivos));
    }
}
