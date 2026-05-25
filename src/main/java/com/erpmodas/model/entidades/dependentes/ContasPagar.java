package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Fornecedor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "contas_pagar")
public class ContasPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_lancamento", nullable = false)
    private LocalDate dataLancamento = LocalDate.now();
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valor;
    @Column(name = "numero_parcela")
    @ToString.Include
    private Integer numeroParcela;
    @Column(name = "total_parcelas")
    private Integer totalParcelas;
    @Column(name = "observacoes", length = 400)
    private String observacoes;
    @Column(name = "status_conta")
    @ToString.Include
    private StatusConta statusConta;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @Transient
    public boolean estaVencida() {

        if(dataPagamento != null){
            return false;
        }

        if(dataVencimento == null){
            return false;
        }

        return dataVencimento.isBefore(LocalDate.now());
    }
}
