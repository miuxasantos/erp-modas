package com.erpmodas.service.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.mapper.dependentes.ContasPagarMapper;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.repository.CompraRepository;
import com.erpmodas.repository.dependentes.ContasPagarRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContasPagarService {

    private final ContasPagarRepository repository;
    private final ContasPagarMapper mapper;
    private final CaixaService caixaService;

    @Transactional
    public List<ContasPagarDTO> listar() {
        List<ContasPagar> lista = repository.findAll();

        lista.forEach(cp -> {
            System.out.println("ContasPagar id: " + cp.getId());
            System.out.println("Compra: " + cp.getCompra());
            System.out.println("Fornecedor: " + (cp.getCompra() != null ? cp.getCompra().getFornecedor() : "null"));
            System.out.println("Nome: " + (cp.getCompra() != null && cp.getCompra().getFornecedor() != null ? cp.getCompra().getFornecedor().getNome() : "null"));
        });
        return mapper.toDTOList(lista);
    }

    @Transactional
    public ContasPagarDTO buscarPorId(Long id) {
        return mapper.toDTO(
                repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada."))
        );
    }

    @Transactional
    public ContasPagarDTO criar(ContasPagarDTO dto) {
        ContasPagar entity = mapper.toEntity(dto);
        ContasPagar salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }

    @Transactional
    public ContasPagarDTO atualizar(Long id, ContasPagarDTO dto) {
        ContasPagar entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada."));

        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ContasPagarDTO marcarComoPago(Long id, LocalDate dataPagamento) {
        ContasPagar conta = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada."));

        if (conta.getStatusConta() == StatusConta.PAGO) {
            throw new RuntimeException("Essa conta já foi paga, não é possível repetir o pagamento.");
        }

        if (conta.getStatusConta() == StatusConta.CANCELADO) {
            throw new RuntimeException("Essa conta foi cancelada e não pode ser paga.");
        }

        conta.setStatusConta(StatusConta.PAGO);
        conta.setDataPagamento(dataPagamento);
        caixaService.registrarSaida(conta);

        return mapper.toDTO(repository.save(conta));
    }

    @Transactional
    public List<ContasPagarDTO> marcarVariasComoPago(List<Long> contasId) {
        List<ContasPagar> contas = repository.findAllById(contasId);
        for (ContasPagar conta : contas) {
            if (conta.getStatusConta() == StatusConta.PAGO) {
                throw new RuntimeException("Essa conta " + conta.getValor() + ", " + conta.getId() + ", já foi paga.");
            }
            if (conta.getStatusConta() == StatusConta.CANCELADO) {
                throw new RuntimeException("Essa conta " + conta.getId() + " foi cancelada.");
            }
        }

        for (ContasPagar conta : contas) {
            conta.setStatusConta(StatusConta.PAGO);
            conta.setDataPagamento(LocalDate.now());
            caixaService.registrarSaida(conta);
        }

        List<ContasPagar> contasSalvas = repository.saveAll(contas);
        return mapper.toDTOList(contasSalvas);
    }

    @Transactional
    public List<ContasPagarDTO> gerarContasPorCompra(Compra compra) {
        List<ContasPagar> contas = new ArrayList<>();
        BigDecimal valorTotal = compra.getValorTotal();

        if (compra.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO) {
            contas = criarContasParceladas(compra, valorTotal);
        }  else {
            ContasPagar conta = criarContaAVista(compra, valorTotal);
            contas.add(conta);
        }

        List<ContasPagar> contasSalvas = repository.saveAll(contas);
        return mapper.toDTOList(contasSalvas);
    }

    private ContasPagar criarContaAVista(Compra compra, BigDecimal valorTotal) {
        ContasPagar conta = new ContasPagar();
        conta.setCompra(compra);
        conta.setValor(valorTotal);
        conta.setDataVencimento(calcularDataVencimento(compra, 1));
        conta.setStatusConta(StatusConta.PENDENTE);
        conta.setNumeroParcela(1);
        conta.setTotalParcelas(1);
        return conta;
    }

    private List<ContasPagar> criarContasParceladas(Compra compra, BigDecimal valorTotal) {
        List<ContasPagar> contas = new ArrayList<>();

        int numeroParcelas = compra.getNumeroParcelas();
        BigDecimal valorParcela = valorTotal.divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.HALF_UP);

        for(int i = 1; i <= numeroParcelas; i++) {
            ContasPagar conta = new ContasPagar();
            conta.setCompra(compra);
            conta.setValor(valorParcela);
            conta.setDataVencimento(LocalDate.now().plusDays(30L * i));
            conta.setNumeroParcela(i);
            conta.setTotalParcelas(numeroParcelas);
            conta.setStatusConta(StatusConta.PENDENTE);
            contas.add(conta);
        }
        return ajustarUltimaParcela(contas, valorTotal);
    }

    private List<ContasPagar> ajustarUltimaParcela(List<ContasPagar> contas, BigDecimal valorTotal) {
        if (contas == null || contas.isEmpty()) {
            return contas;
        }

        BigDecimal somaParcelas = contas.stream()
                .map(ContasPagar::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal diferenca = valorTotal.subtract(somaParcelas);

        if (diferenca.compareTo(BigDecimal.ZERO) != 0) {
            ContasPagar ultimaParcela = contas.get(contas.size() - 1);
            ultimaParcela.setValor(ultimaParcela.getValor().add(diferenca));
        }

        return contas;
    }

    @Transactional
    public void deletarPorCompra(Compra compra) {
        if (compra == null || compra.getId() == null) {
            throw new RuntimeException("Compra não encontrada.");
        }

        List<ContasPagar> contas = repository.findByCompraId(compra.getId());

        if (!contas.isEmpty()) {
            repository.deleteAll(contas);
            log.info("Compra deletada, por isso as contas foram deletadas juntamente.");
        }
    }

    private LocalDate calcularDataVencimento(Compra compra, Integer numeroParcelas) {
        return compra.getDataChegada().plusDays(30L * numeroParcelas);
    }

    @Transactional
    public ContasPagarDTO marcarComoVencido(Long id) {
        ContasPagar conta = repository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada."));

        if(conta.getDataPagamento() != null) {
            return mapper.toDTO(conta);
        }

        if(conta.getDataVencimento() == null) {
            throw new IllegalStateException("Conta a pagar sem data de vencimento.");
        }

        StatusConta novoStatus = conta.getDataVencimento().isBefore(LocalDate.now()) ? StatusConta.VENCIDO : StatusConta.PENDENTE;
        conta.setStatusConta(novoStatus);
        return mapper.toDTO(repository.save(conta));
    }

    @Transactional
    public List<ContasPagarDTO> listarPorStatus(StatusConta statusConta) {
        return mapper.toDTOList(repository.findByStatusConta(statusConta));
    }

    @Transactional
    public List<ContasPagarDTO> listarEmAberto() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.PENDENTE));
    }

    @Transactional
    public List<ContasPagarDTO> listarPagas() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.PAGO));
    }

    @Transactional
    public List<ContasPagarDTO> listarVencidas() {
        return mapper.toDTOList(repository.findByStatusConta(StatusConta.VENCIDO));
    }
}
