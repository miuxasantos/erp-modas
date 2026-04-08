package com.erpmodas.model.entidades.especial;

import com.erpmodas.enums.TipoAcaoAud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "usuario", nullable = false)
    @ToString.Include
    private String usuario;
    @Column(name = "acao", nullable = false)
    @ToString.Include
    private TipoAcaoAud tipoAcaoAud;
    @Column(name = "entidade", nullable = false)
    @ToString.Include
    private String entidade;
    @Column(name = "data_hora", nullable = false)
    @ToString.Include
    private LocalDateTime dataHora = LocalDateTime.now();
}
