package com.erpmodas.service.apoio;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.mapper.apoio.CorMapper;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.repository.apoio.CorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorService {

    private final CorRepository repository;
    private final CorMapper mapper;

    @Transactional
    public CorDTO salvar(CorDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Cor entity =  mapper.toEntity(dto);
        Cor salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<CorDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CorDTO buscarPorId(Long id) {
        Cor cor = repository.findById(id).orElseThrow(() -> new RuntimeException("Cor não encontrada."));
        return mapper.toDTO(cor);
    }

    @Transactional
    public CorDTO atualizar(Long id, CorDTO dto) {
        Cor entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Cor não encontrada."));
        validarNomeDuplicado(dto.getNome(), dto.getId());

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
                        throw new RuntimeException("Já existe uma cor com esse nome!");
                    }
                });
    }
}
