package com.erpmodas.model.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nome", nullable = false)
    @ToString.Include
    private String nome;
    @Column(name = "codigo", nullable = false)
    @EqualsAndHashCode.Include
    private Integer codigo;
    @Column(name = "descricao", length = 400)
    private String descricao;
    @Column(name = "ativo", nullable = false)
    @ToString.Include
    private Boolean ativo;
    @Column(name = "preco_custo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCusto;
    @Column(name = "preco_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;
    @Column(name = "data_inclusao", nullable = false)
    private LocalDate dataInclusao;
    @Column(name = "data_desativacao")
    private LocalDate dataDesativacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;
    @Column(name = "tecido")
    private String tecido;
    @Column(name = "marca")
    private String marca;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
    @Column(name = "imagem", length = 400)
    private String imagem;

    @Transient
    public Double getMargemLucro() {
        if(precoCusto == null || precoVenda == null){
            return 0.00;
        }

        BigDecimal lucro = precoVenda.subtract(precoCusto);

        return lucro.divide(precoVenda, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
    }
}
