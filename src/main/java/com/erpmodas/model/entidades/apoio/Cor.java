package com.erpmodas.model.entidades.apoio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "cor")
public class Cor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @ToString.Include
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "codigoHex", nullable = false)
    private String codigoHex;

}
