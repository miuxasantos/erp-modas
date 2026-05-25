package com.erpmodas.mapper;

import com.erpmodas.dto.compra.CompraDTO;
import com.erpmodas.model.entidades.Compra;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    CompraDTO toDTO(Compra entity);

    Compra toEntity(CompraDTO dto);

    java.util.List<CompraDTO> toDTOList(java.util.List<Compra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CompraDTO dto, @MappingTarget Compra entity);
}
