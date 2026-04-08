package com.erpmodas.model.entidades.apoio;

import com.erpmodas.enums.TamanhoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tamanho")
public class Tamanho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "tamanho", nullable = false)
    private TamanhoEnum tamanho;
}
