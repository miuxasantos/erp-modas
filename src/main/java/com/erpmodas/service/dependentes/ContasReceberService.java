package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.mapper.dependentes.ContasPagarMapper;
import com.erpmodas.mapper.dependentes.ContasReceberMapper;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Venda;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import com.erpmodas.repository.dependentes.ContasPagarRepository;
import com.erpmodas.repository.dependentes.ContasReceberRepository;
import com.erpmodas.service.CaixaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContasReceberService {

    private final ContasReceberRepository repository;
    private final ContasReceberMapper mapper;
    private final CaixaService caixaService;

    @Transactional
    public List<ContasReceberDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional
    public ContasReceberDTO buscarPorId(Long id) {
        return mapper.toDTO(
                repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a receber não encontrada."))
        );
    }

    @Transactional
    public ContasReceberDTO criar(ContasReceberDTO dto) {
        ContasReceber entity = mapper.toEntity(dto);
        ContasReceber salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional
    public ContasReceberDTO atualizar(Long id, ContasReceberDTO dto) {
        ContasReceber entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a receber não encontrada."));

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ContasReceberDTO marcarComoPago(Long id, LocalDate dataRecebimento) {
        ContasReceber conta = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a receber não encontrada."));

        if (conta.getStatusConta() == StatusConta.PAGO) {
            throw new RuntimeException("Essa conta já foi paga, não é possível repetir o pagamento.");
        }

        if (conta.getStatusConta() == StatusConta.CANCELADO) {
            throw new RuntimeException("Essa conta foi cancelada e não pode ser paga.");
        }

        conta.setStatusConta(StatusConta.PAGO);
        conta.setDataRecebimento(dataRecebimento);
        caixaService.registrarEntrada(conta);

        return mapper.toDTO(repository.save(conta));
    }

    @Transactional
    public List<ContasReceberDTO> marcarVariasComoPago(List<Long> contasId) {
        List<ContasReceber> contas = repository.findAllById(contasId);
        for (ContasReceber conta : contas) {
            if (conta.getStatusConta() == StatusConta.PAGO) {
                throw new RuntimeException("Essa conta " + conta.getValor() + ", " + conta.getId() + ", já foi paga.");
            }
            if (conta.getStatusConta() == StatusConta.CANCELADO) {
                throw new RuntimeException("Essa conta " + conta.getId() + " foi cancelada.");
            }
        }

        for (ContasReceber conta : contas) {
            conta.setStatusConta(StatusConta.PAGO);
            conta.setDataRecebimento(LocalDate.now());
            caixaService.registrarEntrada(conta);
        }

        List<ContasReceber> contasSalvas = repository.saveAll(contas);
        return mapper.toDTOList(contasSalvas);
    }

    @Transactional
    public List<ContasReceberDTO> gerarContasPorVenda(Venda venda) {
        List<ContasReceber> contas = new ArrayList<>();
        BigDecimal valorTotal = venda.getValorTotal();

        if (venda.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO) {
            contas = criarContasParceladas(venda, valorTotal);
        }  else {
            ContasReceber conta = criarContaAVista(venda, valorTotal);
            contas.add(conta);
        }

        List<ContasReceber> contasSalvas = repository.saveAll(contas);
        return mapper.toDTOList(contasSalvas);
    }

    private ContasReceber criarContaAVista(Venda venda, BigDecimal valorTotal) {
        ContasReceber conta = new ContasReceber();
        conta.setVenda(venda);
        conta.setValor(valorTotal);
        conta.setDataVencimento(calcularDataVencimento(venda, 1));
        conta.setStatusConta(StatusConta.PENDENTE);
        conta.setNumeroParcela(1);
        conta.setTotalParcelas(1);
        return conta;
    }

    private List<ContasReceber> criarContasParceladas(Venda venda, BigDecimal valorTotal) {
        List<ContasReceber> contas = new ArrayList<>();

        int numeroParcelas = venda.getNumeroParcelas();
        BigDecimal valorParcela = valorTotal.divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.HALF_UP);

        for(int i = 1; i <= numeroParcelas; i++) {
            ContasReceber conta = new ContasReceber();
            conta.setVenda(venda);
            conta.setValor(valorParcela);
            conta.setDataVencimento(LocalDate.now().plusDays(30L * i));
            conta.setNumeroParcela(i);
            conta.setTotalParcelas(numeroParcelas);
            conta.setStatusConta(StatusConta.PENDENTE);
            contas.add(conta);
        }
        return ajustarUltimaParcela(contas, valorTotal);
    }

    private List<ContasReceber> ajustarUltimaParcela(List<ContasReceber> contas, BigDecimal valorTotal) {
        if (contas == null || contas.isEmpty()) {
            return contas;
        }

        BigDecimal somaParcelas = contas.stream()
                .map(ContasReceber::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal diferenca = valorTotal.subtract(somaParcelas);

        if (diferenca.compareTo(BigDecimal.ZERO) != 0) {
            ContasReceber ultimaParcela = contas.get(contas.size() - 1);
            ultimaParcela.setValor(ultimaParcela.getValor().add(diferenca));
        }

        return contas;
    }

    @Transactional
    public void deletarPorVenda(Venda venda) {
        if (venda == null || venda.getId() == null) {
            throw new RuntimeException("Venda não encontrada.");
        }

        List<ContasReceber> contas = repository.findByVendaId(venda.getId());

        if (!contas.isEmpty()) {
            repository.deleteAll(contas);
            log.info("Venda deletada, por isso as contas foram deletadas juntamente.");
        }
    }

    private LocalDate calcularDataVencimento(Venda venda, Integer numeroParcelas) {
        return venda.getDataVenda().plusDays(30L * numeroParcelas);
    }

    @Transactional
    public ContasReceberDTO marcarComoVencido(Long id) {
        ContasReceber conta = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a receber não encontrada."));

        if(conta.getDataRecebimento() != null) {
            return mapper.toDTO(conta);
        }

        if(conta.getDataVencimento() == null) {
            throw new IllegalStateException("Conta a receber sem data de vencimento.");
        }

        StatusConta novoStatus = conta.getDataVencimento().isBefore(LocalDate.now()) ? StatusConta.VENCIDO : StatusConta.PENDENTE;
        conta.setStatusConta(novoStatus);
        return mapper.toDTO(repository.save(conta));
    }

    @Transactional
    public List<ContasReceberDTO> listarPorStatus(StatusConta statusConta) {
        return mapper.toDTOList(repository.findByStatusConta(statusConta));
    }

    @Transactional
    public List<ContasReceberDTO> listarEmAberto() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.PENDENTE));
    }

    @Transactional
    public List<ContasReceberDTO> listarPagas() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.PAGO));
    }

    @Transactional
    public List<ContasReceberDTO> listarVencidas() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.VENCIDO));
    }
}
