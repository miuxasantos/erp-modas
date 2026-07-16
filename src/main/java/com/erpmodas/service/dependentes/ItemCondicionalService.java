package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalReqDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import com.erpmodas.mapper.dependentes.ItemCondicionalMapper;
import com.erpmodas.model.entidades.Condicional;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.model.entidades.dependentes.ItemCondicional;
import com.erpmodas.repository.dependentes.ItemCondicionalRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCondicionalService {
    private final ItemCondicionalRepository repository;
    private final ItemCondicionalMapper mapper;
    private final VariacaoProdutoService variacaoProdutoService;

    @Transactional
    public ItemCondicionalResponseDTO criar(ItemCondicionalReqDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemCondicional item = mapper.toEntity(dto);
        item.setVariacaoProduto(variacaoProduto);

        ItemCondicional salvo = repository.save(item);

        return mapper.toDTO(salvo);
    }

    @Transactional
    public ItemCondicional criarItemEntidade(ItemCondicionalReqDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemCondicional itemCondicional = mapper.toEntity(dto);
        itemCondicional.setVariacaoProduto(variacaoProduto);
        return itemCondicional;
    }

    public ItemCondicionalResponseDTO buscarPorId(Long id) {
        ItemCondicional item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da condicional não encontrado."));
        return mapper.toDTO(item);
    }

    public ItemCondicionalResponseDTO atualizar(Long id, ItemCondicionalResponseDTO dto) {
        ItemCondicional entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da condicional não encontrado."));
        mapper.updateEntityFromDTO(dto, entity);

        ItemCondicional atualizado = repository.save(entity);
        return mapper.toDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorCondicional(Condicional condicional) {
        if (condicional == null || condicional.getId() == null) {
            throw new RuntimeException("Condicional não encontrada.");
        }

        List<ItemCondicional> itens = repository.findByCondicionalId(condicional.getId());

        for (ItemCondicional item : itens) {
            VariacaoProduto variacaoProduto = item.getVariacaoProduto();
            variacaoProduto.setEstoque(variacaoProduto.getEstoque() + item.getQuantidade());
        }

        repository.deleteAll(itens);
    }
}
