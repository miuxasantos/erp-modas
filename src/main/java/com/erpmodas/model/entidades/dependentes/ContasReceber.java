package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.enums.StatusConta;
import com.erpmodas.model.entidades.Venda;
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
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

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
