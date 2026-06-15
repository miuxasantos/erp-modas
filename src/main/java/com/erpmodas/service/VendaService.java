package com.erpmodas.service;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
import com.erpmodas.dto.venda.VendaResponseDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.mapper.VendaMapper;
import com.erpmodas.mapper.dependentes.ItemVendaMapper;
import com.erpmodas.model.entidades.Venda;
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
    public VendaResponseDTO salvar(VendaResponseDTO dto) {

        validarVenda(dto);

        Venda venda = mapper.toEntity(dto);
        venda.setCliente(clienteService.buscarEntidadePorId(dto.getClienteId()));

        processarECalcular(venda, dto.getItensVenda());

        Venda salvo = repository.save(venda);
        contasReceberService.gerarContasPorVenda(salvo);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public VendaResponseDTO buscarPorId(Long id) {
        Venda venda = repository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada."));
        return mapper.toDTO(venda);
    }

    @Transactional(readOnly = true)
    public List<ItemVendaResponseDTO> listarItensDaVenda(Long vendaId) {
        Venda venda = repository.findByIdWithItens(vendaId).orElseThrow(() -> new RuntimeException("Venda não encontrada."));
        return venda.getItensVenda().stream()
                .map(itemVendaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VendaResponseDTO atualizar(Long id, VendaResponseDTO dto) {
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

    private void processarECalcular(Venda venda, List<ItemVendaResponseDTO> itensDTO) {

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemVenda> itensProcessados = new ArrayList<>();

        for (ItemVendaResponseDTO itemVendaResponseDTO : itensDTO) {
            Long variacaoId = itemVendaResponseDTO.getVariacaoProdutoId();

            ItemVenda item = itemVendaService.criarItemEntidade(itemVendaResponseDTO);
            item.setVenda(venda);

            variacaoProdutoService.decrementarEstoque(variacaoId, itemVendaResponseDTO.getQuantidade());

            itensProcessados.add(item);
            valorTotal = valorTotal.add(item.getSubTotal());
        }

        venda.setItensVenda(itensProcessados);
        venda.setValorTotal(valorTotal);
    }

    private void validarVenda(VendaResponseDTO dto) {
        if (dto.getClienteId() == null) {
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
