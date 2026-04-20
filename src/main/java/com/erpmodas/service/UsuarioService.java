package com.erpmodas.service;

import com.erpmodas.dto.usuario.UsuarioDTO;
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
    public UsuarioDTO salvar(UsuarioDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Usuario entity =  mapper.toEntity(dto);
        Usuario salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return mapper.toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        validarNomeDuplicado(dto.getNome(), dto.getId());

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
