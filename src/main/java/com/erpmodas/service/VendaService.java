package com.erpmodas.service;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaDTO;
import com.erpmodas.dto.venda.VendaDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.mapper.VendaMapper;
import com.erpmodas.mapper.dependentes.ItemVendaMapper;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import com.erpmodas.repository.VendaRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import com.erpmodas.service.dependentes.ContasReceberService;
import com.erpmodas.service.dependentes.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository repository;
    private final VendaMapper mapper;
    private final ClienteService clienteService;
    private final VariacaoProdutoService variacaoProdutoService;
    private final ContasReceberService contasReceberService;
    private final ItemVendaService itemVendaService;
    private final ItemVendaMapper itemVendaMapper;

    @Transactional
    public VendaDTO salvar(VendaDTO dto) {

        validarVenda(dto);

        Venda venda = mapper.toEntity(dto);
        venda.setCliente(clienteService.buscarEntidadePorId(dto.getCliente().getId()));

        processarECalcular(venda, dto.getItensVenda());

        Venda salvo = repository.save(venda);
        contasReceberService.gerarContasPorVenda(salvo);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<VendaDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long id) {
        Venda venda = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada."));
        return mapper.toDTO(venda);
    }

    @Transactional(readOnly = true)
    public List<ItemVendaDTO> listarItensDaVenda(Long vendaId) {
        Venda venda = repository.findByIdWithItens(vendaId).orElseThrow(() -> new RuntimeException("Venda não encontrada."));
        return venda.getItensVenda().stream()
                .map(itemVendaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VendaDTO atualizar(Long id, VendaDTO dto) {
        Venda entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada."));
        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public void deletar(Long id) {
        Venda entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        contasReceberService.deletarPorVenda(entity);
        itemVendaService.deletarPorVenda(entity);
        repository.delete(entity);
    }

    private void processarECalcular(Venda venda, List<ItemVendaDTO> itensDTO) {

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemVenda> itensProcessados = new ArrayList<>();

        for (ItemVendaDTO itemVendaDTO : itensDTO) {
            VariacaoProduto variacaoProduto = variacaoProdutoService.buscarEntidadePorId(itemVendaDTO.getVariacaoProduto().getId());

            ItemVenda item = itemVendaService.criarItemEntidade(itemVendaDTO);
            item.setVenda(venda);
            item.setVariacaoProduto(variacaoProduto);

            BigDecimal subTotal = item.getSubTotal();
            valorTotal = valorTotal.add(subTotal);

            variacaoProduto.setEstoque(variacaoProduto.getEstoque() - itemVendaDTO.getQuantidade());

            itensProcessados.add(item);
        }

        venda.setItensVenda(itensProcessados);
        venda.setValorTotal(valorTotal);
    }

    private void validarVenda(VendaDTO dto) {
        if (dto.getCliente().getId() == null) {
            throw new RuntimeException("Cliente é obrigatório");
        }

        if (dto.getItensVenda() == null || dto.getItensVenda().isEmpty()) {
            throw new RuntimeException("Deve haver ao menos um item na venda.");
        }

        if (dto.getFormaPagamento() == null) {
            throw new RuntimeException("Forma de pagamento é obrigatória");
        }

        if (dto.getDataVenda() == null) {
            throw new RuntimeException("A data da venda é obrigatória.");
        }

        if(dto.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO) {
            if(dto.getNumeroParcelas() == null || dto.getNumeroParcelas() <= 1) {
                throw new RuntimeException("Vendas parceladas devem ter mais de 1 parcela.");
            }
        }
    }
}
