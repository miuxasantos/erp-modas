package com.erpmodas.mapper;

import com.erpmodas.dto.marca.MarcaDTO;
import com.erpmodas.model.entidades.Marca;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    MarcaDTO toDTO(Marca marca);

    @Mapping(target = "id", ignore = true)
    Marca toEntity(MarcaDTO marcaDTO);

    java.util.List<MarcaDTO> toDTOList(java.util.List<Marca> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(MarcaDTO dto, @MappingTarget Marca entity);
}
