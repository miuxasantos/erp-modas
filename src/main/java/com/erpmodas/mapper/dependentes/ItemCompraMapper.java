package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemCompra.ItemCompraDTO;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemCompraMapper {

    ItemCompraDTO toDTO(ItemCompra entity);

    ItemCompra toEntity(ItemCompraDTO dto);

    java.util.List<ItemCompraDTO> toDTOList(java.util.List<ItemCompra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ItemCompraDTO dto, @MappingTarget ItemCompra entity);
}
