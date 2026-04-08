package com.erpmodas.service.apoio;

import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.enums.TamanhoEnum;
import com.erpmodas.mapper.apoio.TamanhoMapper;
import com.erpmodas.model.entidades.apoio.Tamanho;
import com.erpmodas.repository.apoio.TamanhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TamanhoService {

    private final TamanhoRepository repository;
    private final TamanhoMapper mapper;

    @Transactional
    public TamanhoDTO salvar(TamanhoDTO dto) {
        validarNomeDuplicado(dto.getTamanho(), null);

        Tamanho entity =  mapper.toEntity(dto);
        Tamanho salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<TamanhoDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public TamanhoDTO buscarPorId(Long id) {
        Tamanho Tamanho = repository.findById(id).orElseThrow(() -> new RuntimeException("Tamanho não encontrado."));
        return mapper.toDTO(Tamanho);
    }

    @Transactional
    public TamanhoDTO atualizar(Long id, TamanhoDTO dto) {
        Tamanho entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Tamanho não encontrado."));
        validarNomeDuplicado(dto.getTamanho(), dto.getId());

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(TamanhoEnum tamanho, Long id) {
        repository.findByTamanho(tamanho)
                .ifPresent(t -> {
                    if(!t.getId().equals(id)) {
                        throw new RuntimeException("Já existe esse tamanho!");
                    }
                });
    }
}
