package com.erpmodas.service;

import com.erpmodas.dto.catalogo.CatalogoResponseDTO;
import com.erpmodas.mapper.CatalogoMapper;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ProdutoRepository repository;
    private final CatalogoMapper mapper;

    @Transactional(readOnly = true)
    public Page<CatalogoResponseDTO> listarPublico(String q, Long categoriaId, Long corId, Long tamanhoId, Pageable pageable) {
        return repository.buscarPublico(q, categoriaId, corId, tamanhoId, pageable)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public CatalogoResponseDTO buscarPorId(Long id) {
        Produto produto = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        return mapper.toDTO(produto);
    }

}
