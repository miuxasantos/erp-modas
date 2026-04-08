package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.enums.FormaPagamento;
import com.erpmodas.enums.StatusCaixa;
import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Fornecedor;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valor;
    @Column(name = "numero_parcelas")
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

        if(dataPagamento != null){
            return false;
        }

        if(dataVencimento == null){
            return false;
        }

        return dataVencimento.isBefore(LocalDate.now());
    }
}
