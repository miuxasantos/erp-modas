package com.erpmodas.model.entidades;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
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
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;
    @Column(name = "lote", nullable = false)
    @ToString.Include
    private String lote;
    @Column(name = "data_chegada", nullable = false)
    @ToString.Include
    private LocalDate dataChegada = LocalDate.now();
    @Column(name = "observacoes", length = 400)
    private String observacoes;
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContasPagar> contasPagar = new ArrayList<>();
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCompra> itensCompra = new ArrayList<>();

    @Transient
    public BigDecimal getValorTotal() {
       return itensCompra.stream().map(ItemCompra::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemCompra itemCompra) {
            itemCompra.setCompra(this);
            itensCompra.add(itemCompra);
    }
}
