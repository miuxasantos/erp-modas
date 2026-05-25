package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalDTO;
import com.erpmodas.model.entidades.dependentes.ItemCondicional;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemCondicionalMapper {

    ItemCondicionalDTO toDTO(ItemCondicional entity);

    ItemCondicional toEntity(ItemCondicionalDTO dto);

    java.util.List<ItemCondicionalDTO> toDTOList(java.util.List<ItemCondicional> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ItemCondicionalDTO dto, @MappingTarget ItemCondicional entity);
}
