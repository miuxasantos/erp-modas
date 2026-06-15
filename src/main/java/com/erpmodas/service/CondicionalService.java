package com.erpmodas.service;

import com.erpmodas.dto.condicional.CondicionalResponseDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import com.erpmodas.mapper.CondicionalMapper;
import com.erpmodas.mapper.dependentes.ItemCondicionalMapper;
import com.erpmodas.model.entidades.Condicional;
import com.erpmodas.model.entidades.dependentes.ItemCondicional;
import com.erpmodas.repository.CondicionalRepository;
import com.erpmodas.service.apoio.VariacaoProdutoService;
import com.erpmodas.service.dependentes.ItemCondicionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CondicionalService {

    private final CondicionalRepository repository;
    private final CondicionalMapper mapper;
    private final ClienteService clienteService;
    private final VariacaoProdutoService variacaoProdutoService;
    private final ItemCondicionalService itemCondicionalService;
    private final ItemCondicionalMapper itemCondicionalMapper;

    @Transactional
    public CondicionalResponseDTO salvar(CondicionalResponseDTO dto) {

        validarCondicional(dto);

        Condicional condicional = mapper.toEntity(dto);
        condicional.setCliente(clienteService.buscarEntidadePorId(dto.getClienteId()));

        processar(condicional, dto.getItensCondicional());

        Condicional salvo = repository.save(condicional);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<CondicionalResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CondicionalResponseDTO buscarPorId(Long id) {
        Condicional condicional = repository.findById(id).orElseThrow(() -> new RuntimeException("Condicional não encontrada."));
        return mapper.toDTO(condicional);
    }

    @Transactional(readOnly = true)
    public List<ItemCondicionalResponseDTO> listarItensDaCondicional(Long condicionalId) {
        Condicional condicional = repository.findByIdWithItens(condicionalId).orElseThrow(() -> new RuntimeException("Condicional não encontrada."));
        return condicional.getItensCondicional().stream()
                .map(itemCondicionalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CondicionalResponseDTO atualizar(Long id, CondicionalResponseDTO dto) {
        Condicional entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Condicional não encontrada."));
        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public void deletar(Long id) {
        Condicional entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Condicional não encontrada"));

        itemCondicionalService.deletarPorCondicional(entity);
        repository.delete(entity);
    }

    private void processar(Condicional condicional, List<ItemCondicionalResponseDTO> itensDTO) {
        List<ItemCondicional> itensProcessados = new ArrayList<>();

        for (ItemCondicionalResponseDTO itemCondicionalResponseDTO : itensDTO) {
            Long variacaoId = itemCondicionalResponseDTO.getVariacaoProdutoId();

            ItemCondicional item = itemCondicionalService.criarItemEntidade(itemCondicionalResponseDTO);
            item.setCondicional(condicional);

            variacaoProdutoService.decrementarEstoque(variacaoId, itemCondicionalResponseDTO.getQuantidade());

            itensProcessados.add(item);
        }
        condicional.setItensCondicional(itensProcessados);
    }

    private void validarCondicional(CondicionalResponseDTO dto) {
        if (dto.getClienteId() == null) {
            throw new RuntimeException("Cliente é obrigatório");
        }

        if (dto.getItensCondicional() == null || dto.getItensCondicional().isEmpty()) {
            throw new RuntimeException("Deve haver ao menos um item na condicional.");
        }

        if (dto.getDataInicio() == null) {
            throw new RuntimeException("A data da condicional é obrigatória.");
        }
    }
}
