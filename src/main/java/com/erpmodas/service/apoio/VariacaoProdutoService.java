package com.erpmodas.service.apoio;

import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.mapper.apoio.VariacaoProdutoMapper;
import com.erpmodas.repository.apoio.VariacaoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariacaoProdutoService {

    private final VariacaoProdutoRepository repository;
    private final VariacaoProdutoMapper mapper;

    @Transactional
    public VariacaoProdutoDTO salvar(VariacaoProdutoDTO dto) {
        validarNomeDuplicado(dto.getSku(), null);

        VariacaoProduto entity =  mapper.toEntity(dto);
        VariacaoProduto salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<VariacaoProdutoDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public VariacaoProdutoDTO buscarPorId(Long id) {
        VariacaoProduto variacaoProduto = repository.findById(id).orElseThrow(() -> new RuntimeException("Variação do produto não encontrado."));
        return mapper.toDTO(variacaoProduto);
    }

    @Transactional
    public VariacaoProdutoDTO atualizar(Long id, VariacaoProdutoDTO dto) {
        VariacaoProduto entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Variação do produto não encontrado."));
        validarNomeDuplicado(dto.getSku(), dto.getId());

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String sku, Long id) {
        repository.findBySku(sku)
                .ifPresent(vp -> {
                    if(!vp.getId().equals(id)) {
                        throw new RuntimeException("Já existe uma variação de produto com esse código SKU!");
                    }
                });
    }
}
