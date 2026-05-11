package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.mapper.dependentes.MovimentacoesCaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import com.erpmodas.repository.CaixaRepository;
import com.erpmodas.repository.dependentes.MovimentacoesCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacoesCaixaService {

    private final MovimentacoesCaixaRepository repository;
    private final CaixaRepository caixaRepository;
    private final MovimentacoesCaixaMapper mapper;

    @Transactional
    public MovimentacoesCaixaDTO criar(Long caixaId, MovimentacoesCaixaDTO dto) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado."));

        if(caixa.getStatusCaixa() != StatusCaixa.ABERTO) {
            throw new RuntimeException("O caixa fechado não pode mais receber movimentações.");
        }

        if (dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O valor de abertura não pode ser menor do que zero.");
        }

        MovimentacoesCaixa entity = mapper.toEntity(dto);
        entity.setCaixa(caixa);

        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public List<MovimentacoesCaixaDTO> listarPorCaixa(Long caixaId) {
        return mapper.toDTOList(repository.findByCaixaId(caixaId));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
