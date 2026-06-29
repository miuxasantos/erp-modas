package com.erpmodas.dto.produto;

import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.dto.apoio.TecidoDTO;
import com.erpmodas.dto.categoria.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private Integer codigo;
    private String descricao;
    private Boolean ativo;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private LocalDate dataInclusao;
    private LocalDate dataDesativacao;
    private TecidoDTO tecido;
    private MarcaDTO marca;
    private CategoriaDTO categoria;
    private String imagem;
}
