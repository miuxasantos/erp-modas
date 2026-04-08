package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Cliente;
import com.erpmodas.model.entidades.Venda;
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
@Table(name = "contas_receber")
public class ContasReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_lancamento", nullable = false)
    private LocalDate dataLancamento = LocalDate.now();
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;
    @Column(name = "data_recebimento")
    private LocalDate dataRecebimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valor;
    @Column(name = "numero_oarcelas")
    @ToString.Include
    private Integer numeroParcelas;
    @Column(name = "forma_pagamento")
    @ToString.Include
    private FormaPagamento formaPagamento;
    @Column(name = "observacoes", length = 400)
    private String observacoes;
    @Column(name = "status_conta")
    @ToString.Include
    private StatusConta statusConta;

    @Transient
    public boolean estaVencida() {

        if(dataRecebimento != null){
            return false;
        }

        if(dataVencimento == null){
            return false;
        }

        return dataVencimento.isBefore(LocalDate.now());
    }
}
