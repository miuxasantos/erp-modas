package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "Marca")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @ToString.Include
    @Column(name = "nome")
    private String nome;
    @Column(name = "observacoes")
    private String observacoes;
}
