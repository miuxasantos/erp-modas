package com.erpmodas.service;


import com.erpmodas.dto.sessiontoken.SessionTokenDTO;
import com.erpmodas.mapper.SessionTokenMapper;
import com.erpmodas.model.entidades.SessionToken;
import com.erpmodas.repository.SessionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionTokenService {

    private final SessionTokenRepository repository;
    private final SessionTokenMapper mapper;

    @Transactional
    public SessionTokenDTO salvar(SessionTokenDTO dto) {

        SessionToken entity =  mapper.toEntity(dto);
        SessionToken salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<SessionTokenDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public SessionTokenDTO buscarPorId(Long id) {
        SessionToken cor = repository.findById(id).orElseThrow(() -> new RuntimeException("SessionToken não encontrado."));
        return mapper.toDTO(cor);
    }

    @Transactional
    public SessionTokenDTO atualizar(Long id, SessionTokenDTO dto) {
        SessionToken entity = repository.findById(id).orElseThrow(() -> new RuntimeException("SessionToken não encontrado."));

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
