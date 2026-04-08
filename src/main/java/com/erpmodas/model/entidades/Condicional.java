package com.erpmodas.model.entidades;

import com.erpmodas.model.entidades.dependentes.ItemCondicional;
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
@Table(name = "condicional")
public class Condicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @Column(name = "data_inicio", nullable = false)
    @ToString.Include
    private LocalDate dataInicio = LocalDate.now();
    @Column(name = "periodo", nullable = false)
    @ToString.Include
    private Integer periodo;
    @OneToMany(mappedBy = "condicional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCondicional> itensCondicional = new ArrayList<>();

    @Transient
    public LocalDate getPrazoEntrega() {
        if(dataInicio == null || periodo == null) {
            return null;
        }

        return dataInicio.plusDays(periodo);
    }

    @Transient
    public boolean estaVencida() {

        LocalDate prazo = getPrazoEntrega();

        if(prazo == null){
            return false;
        }

        return prazo.isBefore(LocalDate.now());
    }

    @Transient
    public Integer getTotalItens(){
        return itensCondicional.stream()
                .mapToInt(ItemCondicional::getQuantidade)
                .sum();
    }

    @Transient
    public BigDecimal getValorTotal(){

        return itensCondicional.stream()
                .map(ItemCondicional::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemCondicional itemCondicional) {
        itemCondicional.setCondicional(this);
        itensCondicional.add(itemCondicional);
    }
}
