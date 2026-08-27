package com.erpmodas.dto.catalogo;

import com.erpmodas.dto.categoria.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoResponseDTO {

    private Long id;
    private String nome;
    private BigDecimal precoVenda;
    private String imagem;
    private CategoriaDTO categoriaDTO;
    private String tecido;
    private List<VariacaoCatalogoResponseDTO> variacoes;
}
