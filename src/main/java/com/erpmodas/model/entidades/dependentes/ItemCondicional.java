package com.erpmodas.model.entidades.dependentes;

import com.erpmodas.model.entidades.Condicional;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "item_condicional", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_condicional", "id_variacaoProduto"})
})
public class ItemCondicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variacao_produto", nullable = false)
    @ToString.Exclude
    private VariacaoProduto variacaoProduto;
    @Min(1)
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condicional", nullable = false)
    @ToString.Exclude
    private Condicional condicional;

    @Transient
    public BigDecimal getValorUnitario(){
        return variacaoProduto.precoVendaFinal();
    }

    @Transient
    public BigDecimal getSubTotal(){

        if(quantidade == null){
            return BigDecimal.ZERO;
        }

        return getValorUnitario().multiply(BigDecimal.valueOf(quantidade));
    }

}
