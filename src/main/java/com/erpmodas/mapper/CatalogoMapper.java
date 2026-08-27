package com.erpmodas.mapper;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.dto.catalogo.CatalogoResponseDTO;
import com.erpmodas.dto.catalogo.VariacaoCatalogoResponseDTO;
import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.model.entidades.Categoria;
import com.erpmodas.model.entidades.Marca;
import com.erpmodas.model.entidades.Produto;
import com.erpmodas.model.entidades.apoio.Cor;
import com.erpmodas.model.entidades.apoio.Tamanho;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogoMapper {

    public CatalogoResponseDTO toDTO(Produto p) {
        List<VariacaoCatalogoResponseDTO> variacoes = p.getVariacoes() == null ? List.of() :
                p.getVariacoes().stream()
                .map(this::toDTO)
                .toList();

        return new CatalogoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getPrecoVenda(),
                p.getImagem(),
                toCategoria(p.getCategoria()),
                p.getTecido() != null ? p.getTecido().getNome() : null,
                variacoes
        );
    }

    public VariacaoCatalogoResponseDTO toDTO(VariacaoProduto v) {
        return new VariacaoCatalogoResponseDTO(
                v.getId(),
                toCor(v.getCor()),
                toTamanho(v.getTamanho()),
                v.getEstoque() != null && v.getEstoque() > 0,
                v.getImagemEsp()
        );
    }

    private CategoriaDTO toCategoria(Categoria c) {
        return c == null ? null : new CategoriaDTO(c.getId(), c.getNome());
    }

    private CorDTO toCor(Cor c) {
        return c == null ? null : new CorDTO(c.getId(), c.getNome(), c.getCodigoHex());
    }

    private TamanhoDTO toTamanho(Tamanho t) {
        return t == null ? null : new TamanhoDTO(t.getId(), t.getTamanho());
    }
}
