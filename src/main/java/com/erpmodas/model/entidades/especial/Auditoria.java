package com.erpmodas.model.entidades.especial;

import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.model.entidades.Usuario;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "acao", nullable = false)
    @ToString.Include
    private TipoAcaoAud tipoAcaoAud;
    @Column(name = "entidade", nullable = false)
    @ToString.Include
    private String entidade;
    @Column(name = "entidade_id", nullable = false)
    private Long entidadeId;
    @Column(name = "data_hora", nullable = false)
    @ToString.Include
    private LocalDateTime dataHora = LocalDateTime.now();
}
