package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "assessoria")
public class Assessoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @ToString.Include
    @Column(name = "nome")
    private String nome;
    @Column(name = "contato")
    private String contato;
}
