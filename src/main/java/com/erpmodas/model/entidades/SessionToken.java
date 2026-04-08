package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "sessionToken")
public class SessionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "token", nullable = false)
    @ToString.Include
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    @Column(name = "data_criacao", nullable = false)
    @ToString.Include
    private LocalDateTime dataCriacao;
    @Column(name = "dataExp", nullable = false)
    @ToString.Include
    private LocalDateTime dataExp;
    @Column(name = "ativo")
    @ToString.Include
    private Boolean ativo;
}
