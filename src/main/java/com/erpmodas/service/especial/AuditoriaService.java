package com.erpmodas.service.especial;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.dto.especial.AuditoriaDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.mapper.apoio.CorMapper;
import com.erpmodas.mapper.especial.AuditoriaMapper;
import com.erpmodas.model.entidades.Usuario;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.model.entidades.especial.Auditoria;
import com.erpmodas.repository.UsuarioRepository;
import com.erpmodas.repository.apoio.CorRepository;
import com.erpmodas.repository.especial.AuditoriaRepository;
import com.erpmodas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository repository;
    private final AuditoriaMapper mapper;
    private final UsuarioService usuarioService;

    @Transactional
    public AuditoriaDTO salvar(AuditoriaDTO dto) {

        Auditoria entity =  mapper.toEntity(dto);
        Auditoria salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(Long usuarioId, TipoAcaoAud acaoAud, String entidade, Long entidadeId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);

        Auditoria auditoria = new Auditoria();
        auditoria.setEntidadeId(entidadeId);
        auditoria.setEntidade(entidade);
        auditoria.setUsuario(usuario);
        auditoria.setTipoAcaoAud(acaoAud);
        auditoria.setDataHora(LocalDateTime.now());

        repository.save(auditoria);
    }

    @Transactional(readOnly = true)
    public List<AuditoriaDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public AuditoriaDTO buscarPorId(Long id) {
        Auditoria auditoria = repository.findById(id).orElseThrow(() -> new RuntimeException("Auditoria não encontrada."));
        return mapper.toDTO(auditoria);
    }

    @Transactional
    public AuditoriaDTO atualizar(Long id, AuditoriaDTO dto) {
        Auditoria entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Auditoria não encontrada."));

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
