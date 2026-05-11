package com.erpmodas.mapper;

import com.erpmodas.dto.produto.ProdutoDTO;
import com.erpmodas.model.entidades.Produto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(source = "compra.id", target = "compraId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProdutoDTO toDTO(Produto entity);

    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Produto toEntity(ProdutoDTO dto);

    java.util.List<ProdutoDTO> toDTOList(java.util.List<Produto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto entity);
}
