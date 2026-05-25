package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaDTO;
import com.erpmodas.mapper.dependentes.ItemVendaMapper;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import com.erpmodas.repository.dependentes.ItemVendaRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemVendaService {

    private final ItemVendaRepository repository;
    private final ItemVendaMapper mapper;
    private final VariacaoProdutoService variacaoProdutoService;

    @Transactional
    public ItemVendaDTO criar(ItemVendaDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemVenda item = mapper.toEntity(dto);
        item.setVariacaoProduto(variacaoProduto);

        item.calcularSubTotal();

        ItemVenda salvo = repository.save(item);

        return mapper.toDTO(salvo);
    }

    @Transactional
    public ItemVenda criarItemEntidade(ItemVendaDTO dto) {
        VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(dto.getVariacaoProdutoId());

        ItemVenda itemVenda = mapper.toEntity(dto);
        itemVenda.setVariacaoProduto(variacaoProduto);
        itemVenda.calcularSubTotal();
        return itemVenda;
    }

    @Transactional
    public ItemVendaDTO buscarPorId(Long id) {
        ItemVenda item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da venda não encontrado."));
        return mapper.toDTO(item);
    }

    @Transactional
    public ItemVendaDTO atualizar(Long id, ItemVendaDTO dto) {
        ItemVenda entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da venda não encontrado."));
        mapper.updateEntityFromDTO(dto, entity);
        entity.calcularSubTotal();

        ItemVenda atualizado = repository.save(entity);
        return mapper.toDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorVenda(Venda venda) {
        if (venda == null || venda.getId() == null) {
            throw new RuntimeException("Venda não encontrada.");
        }

        List<ItemVenda> itens = repository.findByVendaId(venda.getId());

        for (ItemVenda item : itens) {
            VariacaoProduto variacaoProduto = item.getVariacaoProduto();
            variacaoProduto.setEstoque(variacaoProduto.getEstoque() + item.getQuantidade());
        }

        repository.deleteAll(itens);
    }
}
