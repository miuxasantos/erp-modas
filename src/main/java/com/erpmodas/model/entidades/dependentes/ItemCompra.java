package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "item_compra", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_compra", "id_variacao_produto"})
})
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    @ToString.Exclude
    private Compra compra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variacao_produto", nullable = false)
    @ToString.Exclude
    private VariacaoProduto variacaoProduto;
    @DecimalMin("0.01")
    @Column(name = "valor_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnit;
    @Min(1)
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;

    @PrePersist
    @PreUpdate
    public void calcularSubTotal() {
        if (quantidade == null || valorUnit == null) {
            this.subTotal = BigDecimal.ZERO;
        } else {
            this.subTotal = valorUnit.multiply(BigDecimal.valueOf(quantidade));
        }
    }
}
