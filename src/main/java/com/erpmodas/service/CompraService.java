package com.erpmodas.service;

import com.erpmodas.dto.compra.CompraResponseDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraResponseDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.mapper.CompraMapper;
import com.erpmodas.mapper.dependentes.ItemCompraMapper;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import com.erpmodas.repository.CompraRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import com.erpmodas.service.dependentes.ContasPagarService;
import com.erpmodas.service.dependentes.ItemCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository repository;
    private final FornecedorService fornecedorService;
    private final VariacaoProdutoService variacaoProdutoService;
    private final ContasPagarService contasPagarService;
    private final ItemCompraService itemCompraService;
    private final CompraMapper mapper;
    private final ItemCompraMapper itemCompraMapper;

    @Transactional
    public CompraResponseDTO salvar(CompraResponseDTO dto) {

        validarCompra(dto);

        Compra compra = mapper.toEntity(dto);
        compra.setFornecedor(fornecedorService.buscarEntidadePorId(dto.getFornecedorId()));

        processarECalcular(compra, dto.getItensCompra());

        Compra salvo = repository.save(compra);

        contasPagarService.gerarContasPorCompra(salvo);

        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<CompraResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CompraResponseDTO buscarPorId(Long id) {
        Compra compra = repository.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada."));
        return mapper.toDTO(compra);
    }

    @Transactional(readOnly = true)
    public List<ItemCompraResponseDTO> listarItensDaCompra(Long compraId) {
        Compra compra = repository.findByIdWithItens(compraId).orElseThrow(() -> new RuntimeException("Compra não encontrada."));
        return compra.getItensCompra().stream()
                .map(itemCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompraResponseDTO atualizar(Long id, CompraResponseDTO dto) {
        Compra entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada."));
        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public void deletar(Long id) {
        Compra entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada"));

        contasPagarService.deletarPorCompra(entity);
        itemCompraService.deletarPorCompra(entity);
        repository.delete(entity);
    }

    private void processarECalcular(Compra compra, List<ItemCompraResponseDTO> itensDTO) {

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemCompra> itensProcessados = new ArrayList<>();

        for (ItemCompraResponseDTO itemCompraResponseDTO : itensDTO) {
            Long variacaoId = itemCompraResponseDTO.getVariacaoProdutoId();

            ItemCompra item = itemCompraService.criarItemEntidade(itemCompraResponseDTO);
            item.setCompra(compra);

            variacaoProdutoService.incrementarEstoque(variacaoId, itemCompraResponseDTO.getQuantidade());

            itensProcessados.add(item);
            valorTotal = valorTotal.add(item.getSubTotal());
        }

        compra.setItensCompra(itensProcessados);
        compra.setValorTotal(valorTotal);
    }

    private void validarCompra(CompraResponseDTO dto) {
        if (dto.getFornecedorId() == null) {
            throw new RuntimeException("Fornecedor é obrigatório");
        }

        if (dto.getItensCompra() == null || dto.getItensCompra().isEmpty()) {
            throw new RuntimeException("Deve haver ao menos um item na compra.");
        }

        if (dto.getFormaPagamento() == null) {
            throw new RuntimeException("Forma de pagamento é obrigatória");
        }

        if (dto.getDataChegada() == null) {
            throw new RuntimeException("A data de chegada é obrigatória.");
        }

        if(dto.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO) {
            if(dto.getNumeroParcelas() == null || dto.getNumeroParcelas() <= 1) {
                throw new RuntimeException("Compras parceladas devem ter mais de 1 parcela.");
            }
        }
    }
}
