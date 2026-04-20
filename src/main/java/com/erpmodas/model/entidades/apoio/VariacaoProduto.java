package com.erpmodas.model.entidades.apoio;

import com.erpmodas.model.entidades.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "variacao_produto", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"id_produto", "id_cor", "id_tamanho"}
        )
})
public class VariacaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "sku", nullable = false, length = 100)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String sku;
    @Column(name = "estoque", nullable = false)
    @ToString.Include
    private Integer estoque = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;
    @Column(name = "preco_custo", precision = 10, scale = 2)
    private BigDecimal precoCusto;
    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;
    @Column(name = "imagem-esp", length = 400)
    private String imagemEsp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cor", nullable = false)
    private Cor cor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tamanho", nullable = false)
    private Tamanho tamanho;

    // mover para o service

    @PrePersist
    @PreUpdate
    public void gerarSku() {

        if(produto == null || cor == null || tamanho == null){
            return;
        }

        String corSku = cor.getNome()
                .replaceAll("\\s+", "-")
                .toUpperCase();

        String tamanhoSku = tamanho.getTamanho().getSku();

        this.sku = produto.getCodigo() + "-" + corSku + "-" + tamanhoSku;
    }

    public BigDecimal precoVendaFinal() {
        if(precoVenda != null) {
            return precoVenda;
        }

        return produto.getPrecoVenda();
    }

    public BigDecimal precoCustoFinal() {
        if(precoCusto != null) {
            return precoCusto;
        }

        return produto.getPrecoCusto();
    }

    @Transient
    public Double getMargemLucro() {
        BigDecimal custo = precoCustoFinal();
        BigDecimal venda = precoVendaFinal();

            if(custo == null || venda == null){
                return 0.00;
            }

        BigDecimal lucro = venda.subtract(custo);

        return lucro.divide(venda, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
    }
}
