package com.erpmodas.model.entidades;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @Column(name = "data_venda", nullable = false)
    @ToString.Include
    private LocalDate dataVenda = LocalDate.now();
    @Column(name = "observacoes", length = 400)
    private String observacoes;
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContasReceber> contasReceber = new ArrayList<>();
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemVenda> itensVenda = new ArrayList<>();
    @Column(name = "desconto")
    @Min(0)
    @Max(100)
    @ToString.Include
    private Double desconto;

    @Transient
    public BigDecimal getValorTotal() {
        return itensVenda.stream().map(ItemVenda::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public BigDecimal getValorTotalComDesconto() {
        BigDecimal total = getValorTotal();

        if(desconto == null) {
            return total;
        }

        BigDecimal porcentagem = BigDecimal.valueOf(desconto).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        BigDecimal valorDesconto = total.multiply(porcentagem);

        return total.subtract(valorDesconto);
    }

    @Transient
    public BigDecimal getValorDesconto(){

        if(desconto == null){
            return BigDecimal.ZERO;
        }

        BigDecimal total = getValorTotal();

        BigDecimal porcentagem = BigDecimal.valueOf(desconto)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        return total.multiply(porcentagem);
    }

    public void adicionarItem(ItemVenda itemVenda) {
        itemVenda.setVenda(this);
        itensVenda.add(itemVenda);
    }
}
