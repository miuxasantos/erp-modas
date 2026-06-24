package com.erpmodas.dto.produto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoReqDTO {
    @NotNull
    private String nome;
    @NotNull
    private Integer codigo;
    private String descricao;
    @NotNull
    private Boolean ativo;
    @NotNull
    private BigDecimal precoCusto;
    @NotNull
    private BigDecimal precoVenda;
    @NotNull
    private LocalDate dataInclusao;
    private String tecido;
    private String marca;
    @NotNull
    private Long categoriaId;
    private String imagem;
}
