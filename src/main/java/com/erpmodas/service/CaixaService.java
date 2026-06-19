package com.erpmodas.service;

import com.erpmodas.dto.caixa.CaixaReqDTO;
import com.erpmodas.dto.caixa.CaixaResponseDTO;
import com.erpmodas.dto.caixa.CaixaUpdateDTO;
import com.erpmodas.enums.OrigemMov;
import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.enums.TipoMovCaixa;
import com.erpmodas.mapper.CaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import com.erpmodas.repository.CaixaRepository;
import com.erpmodas.repository.dependentes.MovimentacoesCaixaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaixaService {

    private final CaixaRepository repository;
    private final CaixaMapper mapper;
    private final MovimentacoesCaixaRepository movimentacoesCaixaRepository;

    @Transactional
    public CaixaResponseDTO salvar(CaixaReqDTO dto) {
        Caixa entity =  mapper.toEntity(dto);
        Caixa salvo = repository.save(entity);
        return toDTOComCalculos(salvo);
    }

    @Transactional(readOnly = true)
    public List<CaixaResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CaixaResponseDTO buscarPorId(Long id) {
        Caixa caixa = repository.findById(id).orElseThrow(() -> new RuntimeException("Caixa não encontrado."));
        return toDTOComCalculos(caixa);
    }

    @Transactional(readOnly = true)
    public CaixaResponseDTO buscarCaixaAtual() {
        Caixa caixa = repository.findByDataAberturaAndStatusCaixa(LocalDate.now(), StatusCaixa.ABERTO)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado."));
        return toDTOComCalculos(caixa);
    }

    @Transactional
    public CaixaResponseDTO atualizar(Long id, CaixaUpdateDTO dto) {
        Caixa entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Caixa não encontrado."));

        mapper.updateEntityFromDTO(dto, entity);

        Caixa atualizado = repository.save(entity);
        return toDTOComCalculos(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Caixa caixa = repository.findById(id).orElseThrow(() -> new RuntimeException("Caixa não encontrado."));
        List<MovimentacoesCaixa> movimentacoes = movimentacoesCaixaRepository.findByCaixaId(id);
        if(!movimentacoes.isEmpty()) {
            throw new RuntimeException("Esse caixa não pode ser deletado pois há movimentações registradas nele.");
        }
        repository.delete(caixa);
    }

    @Transactional
    public void registrarSaida(ContasPagar conta) {
        Caixa caixa = buscarOuCriarCaixaDoDia();
        if (caixa.getStatusCaixa() != StatusCaixa.ABERTO) {
            throw new RuntimeException("O caixa de hoje " + LocalDate.now() +" não está aberto!");
        }

        MovimentacoesCaixa mov = new MovimentacoesCaixa();
        mov.setTipoMovCaixa(TipoMovCaixa.SAIDA);
        mov.setCaixa(caixa);
        mov.setOrigemMov(OrigemMov.COMPRA);
        mov.setOrigemId(conta.getId());
        mov.setValor(conta.getValor());
        mov.setDescricao(montarDescricaoPagar(conta));

        movimentacoesCaixaRepository.save(mov);
    }

    @Transactional
    public void registrarEntrada(ContasReceber conta) {
        Caixa caixa = buscarOuCriarCaixaDoDia();
        if (caixa.getStatusCaixa() != StatusCaixa.ABERTO) {
            throw new RuntimeException("O caixa de hoje " + LocalDate.now() +" não está aberto!");
        }

        MovimentacoesCaixa mov = new MovimentacoesCaixa();
        mov.setTipoMovCaixa(TipoMovCaixa.ENTRADA);
        mov.setCaixa(caixa);
        mov.setOrigemMov(OrigemMov.VENDA);
        mov.setOrigemId(conta.getId());
        mov.setValor(conta.getValor());
        mov.setDescricao(montarDescricaoReceber(conta));

        movimentacoesCaixaRepository.save(mov);
    }

    @Transactional
    public Caixa buscarOuCriarCaixaDoDia() {
        LocalDate hoje = LocalDate.now();
        Optional<Caixa> caixaOptional = repository.findByDataAberturaAndStatusCaixa(hoje, StatusCaixa.ABERTO);

        if(caixaOptional.isPresent()) {
            return caixaOptional.get();
        }

        Caixa novoCaixa = new Caixa();
        novoCaixa.setDataAbertura(hoje);
        novoCaixa.setSaldoAbertura(BigDecimal.ZERO);
        novoCaixa.setStatusCaixa(StatusCaixa.ABERTO);

        Caixa salvo = repository.save(novoCaixa);
        return salvo;
    }

    @Transactional
    public CaixaResponseDTO fecharCaixaDoDia(Long id) {
        LocalDate hoje = LocalDate.now();

        Caixa caixa = repository.findByIdAndStatusCaixa(id, StatusCaixa.ABERTO)
                .orElseThrow(() -> new RuntimeException("Não há nenhum caixa aberto na data de hoje."));
        CaixaResponseDTO toDTOComCalc = toDTOComCalculos(caixa);

        caixa.setStatusCaixa(StatusCaixa.FECHADO);
        caixa.setDataFechamento(hoje);
        caixa.setSaldoFechamento(toDTOComCalc.getSaldoTotal());

        Caixa fechado = repository.save(caixa);
        return toDTOComCalculos(fechado);
    }

    @Transactional
    public BigDecimal consultarSaldoAtual(CaixaReqDTO dto) {
        Caixa caixa = buscarOuCriarCaixaDoDia();
        toDTOComCalculos(caixa);
        return dto.getSaldoTotal();
    }

    private String montarDescricaoPagar(ContasPagar conta) {
        if (conta.getNumeroParcela() != null && conta.getTotalParcelas() != null) {
            return String.format("Pagamento da parcela ", conta.getNumeroParcela(),
                    conta.getTotalParcelas());
        }
        return "Pagamento à vista";
    }

    private String montarDescricaoReceber(ContasReceber conta) {
        if (conta.getNumeroParcela() != null && conta.getTotalParcelas() != null) {
            return String.format("Pagamento da parcela %d / %d", conta.getNumeroParcela(),
                    conta.getTotalParcelas());
        }
        return "Pagamento à vista";
    }

    private CaixaResponseDTO toDTOComCalculos(Caixa caixa) {
        CaixaResponseDTO dto = mapper.toDTO(caixa);

        List<MovimentacoesCaixa> movs = movimentacoesCaixaRepository.findByCaixaId(caixa.getId());

        BigDecimal entradas = movs.stream()
                .filter(m -> m.getTipoMovCaixa() == TipoMovCaixa.ENTRADA)
                .map(MovimentacoesCaixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saidas = movs.stream()
                .filter(m -> m.getTipoMovCaixa() == TipoMovCaixa.SAIDA)
                .map(MovimentacoesCaixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoTotal = caixa.getSaldoAbertura()
                .add(entradas)
                .subtract(saidas);

        dto.setTotalEntradas(entradas);
        dto.setTotalSaidas(saidas);
        dto.setSaldoTotal(saldoTotal);

        return dto;
    }
}
