package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaDTO;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemVendaMapper {

    ItemVendaDTO toDTO(ItemVenda entity);

    ItemVenda toEntity(ItemVendaDTO dto);

    java.util.List<ItemVendaDTO> toDTOList(java.util.List<ItemVenda> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ItemVendaDTO dto, @MappingTarget ItemVenda entity);
}
