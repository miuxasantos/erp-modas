package com.erpmodas.model.entidades.apoio;

import com.erpmodas.enums.Caimento;
import com.erpmodas.enums.Elasticidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "tecido")
public class Tecido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @ToString.Include
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "gramatura")
    private Integer gramatura;
    @Column(name = "caimento")
    private Caimento caimento;
    @Column(name = "elasticidade")
    private Elasticidade elasticidade;
}
