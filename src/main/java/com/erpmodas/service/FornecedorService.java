package com.erpmodas.service;

import com.erpmodas.dto.fornecedor.FornecedorDTO;
import com.erpmodas.mapper.FornecedorMapper;
import com.erpmodas.model.entidades.Fornecedor;
import com.erpmodas.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    @Transactional
    public FornecedorDTO salvar(FornecedorDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Fornecedor entity =  mapper.toEntity(dto);
        Fornecedor salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<FornecedorDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public FornecedorDTO buscarPorId(Long id) {
        Fornecedor fornecedor = repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
        return mapper.toDTO(fornecedor);
    }

    @Transactional
    public FornecedorDTO atualizar(Long id, FornecedorDTO dto) {
        Fornecedor entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
        validarNomeDuplicado(dto.getNome(), dto.getId());

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findFornecedorByNome(nome)
                .ifPresent(u -> {
                    if(!u.getId().equals(id)) {
                        throw new RuntimeException("Já existe um fornecedor com esse nome!");
                    }
                });
    }
}
