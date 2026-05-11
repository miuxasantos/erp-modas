package com.erpmodas.dto.produto;

import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.dto.compra.CompraDTO;
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
    private CompraDTO compra;
    private String tecido;
    private String marca;
    private CategoriaDTO categoria;
    private String imagem;
}
