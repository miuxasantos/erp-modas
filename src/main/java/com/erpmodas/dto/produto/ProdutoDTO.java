package com.erpmodas.dto.produto;

import com.erpmodas.model.entidades.Categoria;
import com.erpmodas.model.entidades.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private Integer codigo;
    private String descricao;
    private Boolean ativo;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private LocalDate dataInclusao;
    private LocalDate dataDesativacao;
    private Compra compra;
    private String tecido;
    private String marca;
    //trocar categoria e compra para long id
    private Categoria categoria;
    private String imagem;
}
