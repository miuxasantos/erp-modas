package com.erpmodas.service;

import com.erpmodas.dto.fornecedor.FornecedorReqDTO;
import com.erpmodas.dto.fornecedor.FornecedorResponseDTO;
import com.erpmodas.dto.fornecedor.FornecedorUpdateDTO;
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
    private final AssessoriaService assessoriaService;

    @Transactional
    public FornecedorResponseDTO salvar(FornecedorReqDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Fornecedor entity =  mapper.toEntity(dto);
        entity.setAssessoria(assessoriaService.buscarEntidadePorId(dto.getAssessoriaId()));
        Fornecedor salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public FornecedorResponseDTO buscarPorId(Long id) {
        Fornecedor fornecedor = repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
        return mapper.toDTO(fornecedor);
    }

    @Transactional
    public Fornecedor buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
    }

    @Transactional
    public FornecedorResponseDTO atualizar(Long id, FornecedorUpdateDTO dto) {
        Fornecedor entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
        validarNomeDuplicado(dto.getNome(), id);

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
