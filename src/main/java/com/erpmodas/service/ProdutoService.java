package com.erpmodas.service;

import com.erpmodas.dto.produto.ProdutoResponseDTO;
import com.erpmodas.mapper.ProdutoMapper;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final CategoriaService categoriaService;

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoResponseDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Produto entity =  mapper.toEntity(dto);
        entity.setCategoria(categoriaService.buscarEntidadePorId(dto.getCategoriaId()));
        entity.setDataInclusao(LocalDate.now());
        Produto salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        return mapper.toDTO(produto);
    }

    @Transactional
    public Produto buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoResponseDTO dto) {
        Produto entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        validarNomeDuplicado(dto.getNome(), id);

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findProdutoByNome(nome)
                .ifPresent(p -> {
                    if(!p.getId().equals(id)) {
                        throw new RuntimeException("Já existe um produto com esse nome!");
                    }
                });
    }
}
