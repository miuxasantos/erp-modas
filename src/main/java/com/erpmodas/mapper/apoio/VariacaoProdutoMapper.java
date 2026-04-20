package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VariacaoProdutoMapper {
    VariacaoProdutoDTO toDTO(VariacaoProduto entity);

    VariacaoProduto toEntity(VariacaoProdutoDTO dto);

    java.util.List<VariacaoProdutoDTO> toDTOList(java.util.List<VariacaoProduto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(VariacaoProdutoDTO dto, @MappingTarget VariacaoProduto entity);
}
