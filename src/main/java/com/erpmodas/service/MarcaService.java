package com.erpmodas.service;

import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.mapper.MarcaMapper;
import com.erpmodas.model.entidades.Marca;
import com.erpmodas.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {
    private final MarcaRepository repository;
    private final MarcaMapper mapper;

    @Transactional
    public MarcaDTO salvar(MarcaDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Marca entity =  mapper.toEntity(dto);
        Marca salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<MarcaDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public MarcaDTO buscarPorId(Long id) {
        Marca marca = repository.findById(id).orElseThrow(() -> new RuntimeException("Marca não encontrada."));
        return mapper.toDTO(marca);
    }

    public Marca buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Marca não encontrada."));
    }

    @Transactional
    public MarcaDTO atualizar(Long id, MarcaDTO dto) {
        Marca entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Marca não encontrada."));
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
                        throw new RuntimeException("Já existe uma marca com esse nome!");
                    }
                });
    }
}
