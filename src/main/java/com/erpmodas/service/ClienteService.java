package com.erpmodas.service;

import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.mapper.ClienteMapper;
import com.erpmodas.model.entidades.Cliente;
import com.erpmodas.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Transactional
    public ClienteResponseDTO salvar(ClienteResponseDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Cliente entity =  mapper.toEntity(dto);
        Cliente salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        return mapper.toDTO(cliente);
    }

    @Transactional
    public Cliente buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteResponseDTO dto) {
        Cliente entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        validarNomeDuplicado(dto.getNome(), id);

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findClienteByNome(nome)
                .ifPresent(u -> {
                    if(!u.getId().equals(id)) {
                        throw new RuntimeException("Já existe um cliente com esse nome!");
                    }
                });
    }
}
