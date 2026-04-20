package com.erpmodas.mapper;

import com.erpmodas.dto.produto.ProdutoDTO;
import com.erpmodas.model.entidades.Produto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDTO toDTO(Produto entity);

    Produto toEntity(ProdutoDTO dto);

    java.util.List<ProdutoDTO> toDTOList(java.util.List<Produto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto entity);
}
