package com.erpmodas.service;

import com.erpmodas.dto.assessoria.AssessoriaDTO;
import com.erpmodas.mapper.AssessoriaMapper;
import com.erpmodas.model.entidades.Assessoria;
import com.erpmodas.repository.AssessoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessoriaService {

    private final AssessoriaRepository repository;
    private final AssessoriaMapper mapper;

    @Transactional
    public AssessoriaDTO salvar(AssessoriaDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Assessoria entity =  mapper.toEntity(dto);
        Assessoria salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<AssessoriaDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public AssessoriaDTO buscarPorId(Long id) {
        Assessoria assessoria = repository.findById(id).orElseThrow(() -> new RuntimeException("Assessoria não encontrada."));
        return mapper.toDTO(assessoria);
    }

    public Assessoria buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Assessoria não encontrada."));
    }

    @Transactional
    public AssessoriaDTO atualizar(Long id, AssessoriaDTO dto) {
        Assessoria entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Assessoria não encontrada."));
        validarNomeDuplicado(dto.getNome(), id);

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findByNome(nome)
                .ifPresent(c -> {
                    if(!c.getId().equals(id)) {
                        throw new RuntimeException("Já existe uma assessoria com esse nome!");
                    }
                });
    }
}
