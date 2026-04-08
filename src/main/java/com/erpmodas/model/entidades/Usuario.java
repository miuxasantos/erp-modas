package com.erpmodas.model.entidades;


import com.erpmodas.enums.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome", nullable = false)
    @ToString.Include
    private String nome;
    @Column(name = "email", nullable = false)
    @ToString.Include
    @Email
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;
    @Column(name = "cargo", nullable = false)
    private Cargo cargo;
}
