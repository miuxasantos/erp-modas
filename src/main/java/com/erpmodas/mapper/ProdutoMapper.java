package com.erpmodas.mapper;

import com.erpmodas.dto.produto.ProdutoReqDTO;
import com.erpmodas.dto.produto.ProdutoResponseDTO;
import com.erpmodas.dto.produto.ProdutoUpdateDTO;
import com.erpmodas.model.entidades.Produto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProdutoMapper {

    ProdutoResponseDTO toDTO(Produto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "tecido", ignore = true)
    Produto toEntity(ProdutoReqDTO dto);

    java.util.List<ProdutoResponseDTO> toDTOList(java.util.List<Produto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "dataInclusao", ignore = true)
    @Mapping(target = "dataDesativacao", ignore = true)
    @Mapping(target = "tecido", ignore = true)
    void updateEntityFromDTO(ProdutoUpdateDTO dto, @MappingTarget Produto entity);
}
