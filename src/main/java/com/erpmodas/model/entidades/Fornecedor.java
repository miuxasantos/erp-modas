package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome", nullable = false)
    @ToString.Include
    private String nome;
    @Column(name = "contato", nullable = false)
    private String contato;
    @Column(name = "assessoria")
    private String assessoria;
}
