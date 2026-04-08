package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome", nullable = false)
    @ToString.Include
    private String nome;
    @Column(name = "contato", nullable = false)
    @ToString.Include
    private String contato;
    @Column(name = "documento", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String documento;
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "rua")
    private String rua;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
}
