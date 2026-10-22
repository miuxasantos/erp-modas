package com.erpmodas.dto.catalogo;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.dto.categoria.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoFiltrosDTO {
    private List<CategoriaDTO> categorias;
    private List<CorDTO> cores;
    private List<TamanhoDTO> tamanhos;
}
