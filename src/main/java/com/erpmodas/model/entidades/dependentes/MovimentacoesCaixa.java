package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.enums.TipoMovCaixa;
import com.erpmodas.model.entidades.Caixa;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Venda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "movimentacoes_caixa")
public class MovimentacoesCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;
    @Column(name = "data", nullable = false)
    private LocalDate data = LocalDate.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mov_caixa")
    @ToString.Include
    private TipoMovCaixa tipoMovCaixa;
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valor;
    @Column(name = "descricao")
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Venda venda;
    @ManyToOne(fetch = FetchType.LAZY)
    private Compra compra;
}
