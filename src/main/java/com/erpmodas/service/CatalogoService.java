package com.erpmodas.service;

import com.erpmodas.dto.catalogo.CatalogoFiltrosDTO;
import com.erpmodas.dto.catalogo.CatalogoResponseDTO;
import com.erpmodas.mapper.CatalogoMapper;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.repository.ProdutoRepository;
import com.erpmodas.service.apoio.CorService;
import com.erpmodas.service.apoio.TamanhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final CorService corService;
    private final TamanhoService tamanhoService;
    private final CatalogoMapper mapper;

    @Transactional(readOnly = true)
    public Page<CatalogoResponseDTO> listarPublico(String q, Long categoriaId, Long corId, Long tamanhoId, Pageable pageable) {
        return produtoService.buscarAtivos(q, categoriaId, corId, tamanhoId, pageable)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public CatalogoResponseDTO buscarPorId(Long id) {
        return produtoService.buscarAtivoPorId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    @Transactional(readOnly = true)
    public CatalogoFiltrosDTO carregarFiltros() {
        return new CatalogoFiltrosDTO(
                categoriaService.listarComProdutosAtivos(),
                corService.listarComVariacoesAtivas(),
                tamanhoService.listarComVariacoesAtivas()
        );
    }

}
