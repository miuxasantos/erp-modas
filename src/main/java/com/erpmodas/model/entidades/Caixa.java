package com.erpmodas.model.entidades;

import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "caixa")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "data_abertura", nullable = false)
    @ToString.Include
    private LocalDate dataAbertura = LocalDate.now();
    @Column(name = "data_fechamento")
    @ToString.Include
    private LocalDate dataFechamento;
    @Column(name = "saldo_abertura", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal saldoAbertura;
    @Column(name = "saldo_fechamento", precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal saldoFechamento;
    @Column(name = "status_caixa")
    @ToString.Include
    private StatusCaixa statusCaixa;

    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // voltar para lazy
    private List<MovimentacoesCaixa> movimentacoesCaixa = new ArrayList<>();

    // mudar pro service

    @Transient
    public BigDecimal getTotalEntradas() {
        return movimentacoesCaixa.stream()
                .filter(m -> m.getTipoMovCaixa().equals("entrada"))
                .map(MovimentacoesCaixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public BigDecimal getTotalSaidas() {
        return movimentacoesCaixa.stream()
                .filter(m -> m.getTipoMovCaixa().equals("saida"))
                .map(MovimentacoesCaixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public BigDecimal getSaldoTotal() {
        BigDecimal entradas = getTotalEntradas();
        BigDecimal saidas = getTotalSaidas();

        return saldoAbertura
                .add(entradas)
                .subtract(saidas);
    }
}
