package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.itemCompra.ItemCompraDTO;
import com.erpmodas.mapper.dependentes.ItemCompraMapper;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import com.erpmodas.repository.dependentes.ItemCompraRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCompraService {

    private final ItemCompraRepository repository;
    private final ItemCompraMapper mapper;
    private final VariacaoProdutoService variacaoProdutoService;

    @Transactional
    public ItemCompraDTO criar(ItemCompraDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemCompra item = mapper.toEntity(dto);
        item.setVariacaoProduto(variacaoProduto);

        item.calcularSubTotal();

        ItemCompra salvo = repository.save(item);

        return mapper.toDTO(salvo);
    }

    @Transactional
    public ItemCompra criarItemEntidade(ItemCompraDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemCompra itemCompra = mapper.toEntity(dto);
        itemCompra.setVariacaoProduto(variacaoProduto);
        itemCompra.calcularSubTotal();
        return itemCompra;
    }

    public ItemCompraDTO buscarPorId(Long id) {
        ItemCompra item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da compra não encontrado."));
        return mapper.toDTO(item);
    }

    public ItemCompraDTO atualizar(Long id, ItemCompraDTO dto) {
        ItemCompra entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da compra não encontrado."));
        mapper.updateEntityFromDTO(dto, entity);
        entity.calcularSubTotal();

        ItemCompra atualizado = repository.save(entity);
        return mapper.toDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorCompra(Compra compra) {
        if (compra == null || compra.getId() == null) {
            throw new RuntimeException("Compra não encontrada.");
        }

        List<ItemCompra> itens = repository.findByCompraId(compra.getId());

        for (ItemCompra item : itens) {
            VariacaoProduto variacaoProduto = item.getVariacaoProduto();
            variacaoProduto.setEstoque(variacaoProduto.getEstoque() - item.getQuantidade());
        }

        repository.deleteAll(itens);
    }
}
