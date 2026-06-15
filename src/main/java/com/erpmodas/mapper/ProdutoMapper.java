package com.erpmodas.mapper;

import com.erpmodas.dto.produto.ProdutoResponseDTO;
import com.erpmodas.model.entidades.Produto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProdutoMapper {

    ProdutoResponseDTO toDTO(Produto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Produto toEntity(ProdutoResponseDTO dto);

    java.util.List<ProdutoResponseDTO> toDTOList(java.util.List<Produto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntityFromDTO(ProdutoResponseDTO dto, @MappingTarget Produto entity);
}
