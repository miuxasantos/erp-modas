package com.erpmodas.service;

import com.erpmodas.dto.produto.ProdutoReqDTO;
import com.erpmodas.dto.produto.ProdutoResponseDTO;
import com.erpmodas.dto.produto.ProdutoUpdateDTO;
import com.erpmodas.mapper.ProdutoMapper;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.repository.ProdutoRepository;
import com.erpmodas.service.apoio.TecidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final CategoriaService categoriaService;
    private final TecidoService tecidoService;
    private final MarcaService marcaService;

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoReqDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Produto entity =  mapper.toEntity(dto);
        entity.setCategoria(categoriaService.buscarEntidadePorId(dto.getCategoriaId()));
        entity.setTecido(tecidoService.buscarEntidadePorId(dto.getTecidoId()));
        entity.setMarca(marcaService.buscarEntidadePorId(dto.getMarcaId()));
        entity.setDataInclusao(LocalDate.now());
        entity.setNoCatalogo(Boolean.TRUE.equals(dto.getNoCatalogo()));
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

    @Transactional(readOnly = true)
    public Page<Produto> buscarAtivos(String q, Long categoriaId, Long corId, Long tamanhoId, Pageable pageable) {
        return repository.buscarPublico(q, categoriaId, corId, tamanhoId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Produto> buscarAtivoPorId(Long id) {
        return repository.findByIdAndAtivoTrueAndNoCatalogoTrue(id);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoUpdateDTO dto) {
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
