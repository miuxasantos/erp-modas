package com.erpmodas.service;

import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.mapper.CategoriaMapper;
import com.erpmodas.model.entidades.Categoria;
import com.erpmodas.model.entidades.Fornecedor;
import com.erpmodas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    @Transactional
    public CategoriaDTO salvar(CategoriaDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Categoria entity =  mapper.toEntity(dto);
        Categoria salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
        return mapper.toDTO(categoria);
    }

    @Transactional
    public Categoria buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }

    @Transactional
    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
        validarNomeDuplicado(dto.getNome(), id);

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findByNomeContainingIgnoreCase(nome)
                .ifPresent(c -> {
                    if(!c.getId().equals(id)) {
                        throw new RuntimeException("Já existe uma categoria com esse nome!");
                    }
                });
    }
}
