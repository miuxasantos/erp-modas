package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalReqDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import com.erpmodas.model.entidades.dependentes.ItemCondicional;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemCondicionalMapper {

    ItemCondicionalResponseDTO toDTO(ItemCondicional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemCondicional toEntity(ItemCondicionalResponseDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemCondicional toEntity(ItemCondicionalReqDTO dto);

    java.util.List<ItemCondicionalResponseDTO> toDTOList(java.util.List<ItemCondicional> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    void updateEntityFromDTO(ItemCondicionalResponseDTO dto, @MappingTarget ItemCondicional entity);
}
