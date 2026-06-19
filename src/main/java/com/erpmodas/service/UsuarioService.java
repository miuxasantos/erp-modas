package com.erpmodas.service;

import com.erpmodas.dto.usuario.UsuarioReqDTO;
import com.erpmodas.dto.usuario.UsuarioResponseDTO;
import com.erpmodas.dto.usuario.UsuarioUpdateDTO;
import com.erpmodas.mapper.UsuarioMapper;
import com.erpmodas.model.entidades.Usuario;
import com.erpmodas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioReqDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Usuario entity =  mapper.toEntity(dto);
        Usuario salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return mapper.toDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        validarNomeDuplicado(dto.getNome(), id);

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long id) {
        repository.findUsuarioByNome(nome)
                .ifPresent(u -> {
                    if(!u.getId().equals(id)) {
                        throw new RuntimeException("Já existe um usuário com esse nome!");
                    }
                });
    }
}
