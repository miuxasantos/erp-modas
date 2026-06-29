package com.erpmodas.service.apoio;

import com.erpmodas.dto.apoio.TecidoDTO;
import com.erpmodas.mapper.apoio.TecidoMapper;
import com.erpmodas.model.entidades.apoio.Tecido;
import com.erpmodas.repository.apoio.TecidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TecidoService {

    private final TecidoRepository repository;
    private final TecidoMapper mapper;

    @Transactional
    public TecidoDTO salvar(TecidoDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Tecido entity =  mapper.toEntity(dto);
        Tecido salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<TecidoDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public TecidoDTO buscarPorId(Long id) {
        Tecido tecido = repository.findById(id).orElseThrow(() -> new RuntimeException("Tecido não encontrado."));
        return mapper.toDTO(tecido);
    }

    public Tecido buscarEntidadePorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Tecido não encontrado."));
    }

    @Transactional
    public TecidoDTO atualizar(Long id, TecidoDTO dto) {
        Tecido entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Tecido não encontrado."));
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
                        throw new RuntimeException("Já existe um tecido com esse nome!");
                    }
                });
    }
}
